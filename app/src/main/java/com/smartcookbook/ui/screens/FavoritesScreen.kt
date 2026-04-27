package com.smartcookbook.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartcookbook.ui.components.BottomNavBar
import com.smartcookbook.ui.components.BottomNavTab
import com.smartcookbook.ui.components.RecipeCard
import com.smartcookbook.ui.theme.*
import com.smartcookbook.viewmodel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    vm: FavoritesViewModel,
    onRecipeClick: (String) -> Unit,
    onBack: () -> Unit,
    onHomeClick: () -> Unit,
    onShoppingClick: () -> Unit
) {
    val favorites by vm.favoriteRecipes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Favorites", style = MaterialTheme.typography.headlineSmall, color = Gray900)
                        Text("${favorites.size} saved recipe${if (favorites.size != 1) "s" else ""}",
                            style = MaterialTheme.typography.labelMedium, color = Gray400)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
            )
        },
        bottomBar = {
            BottomNavBar(
                currentTab = BottomNavTab.FAVORITES,
                onHomeClick = onHomeClick,
                onFavoritesClick = {},
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
            if (favorites.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier.fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(shape = androidx.compose.foundation.shape.CircleShape,
                            color = Gray100, modifier = Modifier.size(80.dp)) {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                Icon(Icons.Filled.FavoriteBorder, null, tint = Gray200,
                                    modifier = Modifier.size(40.dp))
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                        Text("No saved recipes", style = MaterialTheme.typography.titleLarge, color = Gray900)
                        Text("Tap the heart icon on a recipe to save it here.",
                            style = MaterialTheme.typography.bodyMedium, color = Gray500)
                    }
                }
            } else {
                itemsIndexed(favorites) { _, recipe ->
                    Box {
                        RecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe.id.toString()) })
                        IconButton(
                            onClick = { vm.removeFavorite(recipe.id) },
                            modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                        ) {
                            Icon(Icons.Filled.Favorite, "Remove", tint = Orange500)
                        }
                    }
                }
            }
        }
    }
}
