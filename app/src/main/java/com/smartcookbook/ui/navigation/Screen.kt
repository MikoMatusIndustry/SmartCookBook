package com.smartcookbook.ui.navigation

sealed class Screen(val route: String) {
    object Welcome      : Screen("welcome")
    object Home         : Screen("home")
    object RecipeList   : Screen("recipe_list/{categoryId}") {
        fun createRoute(categoryId: String) = "recipe_list/$categoryId"
    }
    object RecipeDetails: Screen("recipe_details/{recipeId}") {
        fun createRoute(recipeId: String) = "recipe_details/$recipeId"
    }
    object Favorites    : Screen("favorites")
    object ShoppingList : Screen("shopping_list")
    object CookingTimer : Screen("cooking_timer")
}
