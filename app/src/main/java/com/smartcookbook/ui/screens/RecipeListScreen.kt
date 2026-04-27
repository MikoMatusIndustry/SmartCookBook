package com.smartcookbook.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartcookbook.data.SeedData
import com.smartcookbook.data.repository.RecipeRepository
import com.smartcookbook.ui.components.BottomNavBar
import com.smartcookbook.ui.components.BottomNavTab
import com.smartcookbook.ui.components.RecipeCard
import com.smartcookbook.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    categoryId: Int,
    recipeRepo: RecipeRepository,
    onRecipeClick: (String) -> Unit,
    onBack: () -> Unit,
    onFavoritesClick: () -> Unit,
    onShoppingClick: () -> Unit
) {
    val category = SeedData.CATEGORIES.find { it.id == categoryId }
    val recipes = recipeRepo.getRecipesByCategory(categoryId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(category?.title ?: "Recipes", style = MaterialTheme.typography.headlineSmall, color = Gray900) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, "Back", tint = Gray900)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
            )
        },
        bottomBar = {
            BottomNavBar(
                currentTab = BottomNavTab.HOME,
                onHomeClick = onBack,
                onFavoritesClick = onFavoritesClick,
                onShoppingClick = onShoppingClick
            )
        },
        containerColor = Gray50
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (recipes.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text("No recipes in this category.", color = Gray500)
                    }
                }
            } else {
                items(recipes) { recipe ->
                    RecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe.id.toString()) })
                }
            }
        }
    }
}
