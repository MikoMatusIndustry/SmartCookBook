package com.smartcookbook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.smartcookbook.data.local.AppDatabase
import com.smartcookbook.data.repository.RecipeRepository
import com.smartcookbook.data.repository.ShoppingRepository
import com.smartcookbook.ui.screens.*
import com.smartcookbook.viewmodel.*

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val db = AppDatabase.getInstance(context)
    val recipeRepo = RecipeRepository(db)
    val shoppingRepo = ShoppingRepository(db)

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {

        composable(Screen.Welcome.route) {
            WelcomeScreen(onContinue = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Welcome.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Home.route) {
            val vm: HomeViewModel = viewModel(factory = HomeViewModel.Factory(recipeRepo))
            HomeScreen(
                vm = vm,
                onRecipeClick = { navController.navigate(Screen.RecipeDetails.createRoute(it)) },
                onCategoryClick = { navController.navigate(Screen.RecipeList.createRoute(it)) },
                onFavoritesClick = { navController.navigate(Screen.Favorites.route) },
                onShoppingClick = { navController.navigate(Screen.ShoppingList.route) }
            )
        }

        composable(
            route = Screen.RecipeList.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { back ->
            val categoryId = back.arguments?.getString("categoryId")?.toIntOrNull() ?: -1
            RecipeListScreen(
                categoryId = categoryId,
                recipeRepo = recipeRepo,
                onRecipeClick = { navController.navigate(Screen.RecipeDetails.createRoute(it)) },
                onBack = { navController.popBackStack() },
                onFavoritesClick = { navController.navigate(Screen.Favorites.route) },
                onShoppingClick = { navController.navigate(Screen.ShoppingList.route) }
            )
        }

        composable(
            route = Screen.RecipeDetails.route,
            arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
        ) { back ->
            val recipeId = back.arguments?.getString("recipeId")?.toIntOrNull() ?: -1
            val vm: RecipeDetailsViewModel = viewModel(
                key = recipeId.toString(), // <--- NAPRAWIONE: Zmieniamy Int na String
                factory = RecipeDetailsViewModel.Factory(recipeRepo, recipeId)
            )
            val shoppingVm: ShoppingListViewModel = viewModel(
                factory = ShoppingListViewModel.Factory(shoppingRepo)
            )
            RecipeDetailsScreen(
                vm = vm,
                shoppingVm = shoppingVm,
                onBack = { navController.popBackStack() },
                onTimerClick = { navController.navigate(Screen.CookingTimer.route) }
            )
        }

        composable(Screen.Favorites.route) {
            val vm: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory(recipeRepo))
            FavoritesScreen(
                vm = vm,
                onRecipeClick = { navController.navigate(Screen.RecipeDetails.createRoute(it)) },
                onBack = { navController.popBackStack() },
                onHomeClick = { navController.navigate(Screen.Home.route) },
                onShoppingClick = { navController.navigate(Screen.ShoppingList.route) }
            )
        }

        composable(Screen.ShoppingList.route) {
            val vm: ShoppingListViewModel = viewModel(factory = ShoppingListViewModel.Factory(shoppingRepo))
            ShoppingListScreen(
                vm = vm,
                onBack = { navController.popBackStack() },
                onHomeClick = { navController.navigate(Screen.Home.route) },
                onFavoritesClick = { navController.navigate(Screen.Favorites.route) }
            )
        }

        composable(Screen.CookingTimer.route) {
            val vm: CookingTimerViewModel = viewModel()
            CookingTimerScreen(
                vm = vm,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
