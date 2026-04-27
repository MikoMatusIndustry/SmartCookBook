package com.smartcookbook.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.smartcookbook.ui.components.BottomNavBar
import com.smartcookbook.ui.components.BottomNavTab
import com.smartcookbook.ui.components.RecipeCard
import com.smartcookbook.ui.theme.*
import com.smartcookbook.viewmodel.HomeViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    vm: HomeViewModel,
    onRecipeClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onFavoritesClick: () -> Unit,
    onShoppingClick: () -> Unit
) {
    val searchQuery by vm.searchQuery.collectAsState()
    val searchResults by vm.searchResults.collectAsState()

    val greeting = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
        in 0..11  -> "Good Morning!"
        in 12..16 -> "Good Afternoon!"
        else      -> "Good Evening!"
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentTab = BottomNavTab.HOME,
                onHomeClick = {},
                onFavoritesClick = onFavoritesClick,
                onShoppingClick = onShoppingClick
            )
        },
        containerColor = White
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Header
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                    Text(greeting, style = MaterialTheme.typography.displaySmall, color = Gray900)
                    Text("What would you like to cook today?",
                        style = MaterialTheme.typography.bodyMedium, color = Gray500)
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { vm.searchQuery.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search recipes...", color = Gray400) },
                        leadingIcon = { Icon(Icons.Filled.Search, null, tint = Gray400) },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Orange500,
                            unfocusedBorderColor = Gray200,
                            focusedContainerColor = Gray50,
                            unfocusedContainerColor = Gray50
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {})
                    )
                }
            }

            if (searchQuery.isNotBlank()) {
                // Search results
                item {
                    Text(
                        text = if (searchResults.isEmpty()) "No results found" else "Results for \"$searchQuery\"",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Gray900,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                    )
                }
                items(searchResults) { recipe ->
                    RecipeCard(
                        recipe = recipe,
                        onClick = { onRecipeClick(recipe.id.toString()) },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            } else {
                // Recipe of the Day
                item {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text("Recipe of the Day",
                            style = MaterialTheme.typography.headlineSmall, color = Gray900)
                        Spacer(Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .clip(RoundedCornerShape(28.dp))
                                .clickable { onRecipeClick(vm.recipeOfDay.id.toString()) }
                        ) {
                            AsyncImage(
                                model = vm.recipeOfDay.thumbnail,
                                contentDescription = vm.recipeOfDay.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(Color.Transparent, Color(0xDD111827)),
                                            startY = 100f
                                        )
                                    )
                            )
                            Column(
                                modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = Orange500,
                                    modifier = Modifier.padding(bottom = 6.dp)
                                ) {
                                    Text("FEATURED",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = White,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                                }
                                Text(vm.recipeOfDay.title,
                                    style = MaterialTheme.typography.headlineMedium, color = White,
                                    maxLines = 2, overflow = TextOverflow.Ellipsis)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.AccessTime, null,
                                        tint = Orange400, modifier = Modifier.size(16.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text(vm.recipeOfDay.prepTime,
                                        style = MaterialTheme.typography.labelMedium, color = White)
                                }
                            }
                        }
                    }
                }

                // Categories
                item {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
                        Text("Categories",
                            style = MaterialTheme.typography.headlineSmall, color = Gray900)
                        Spacer(Modifier.height(12.dp))
                        val grid = vm.categories.chunked(2)
                        grid.forEach { row ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                row.forEach { cat ->
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(120.dp)
                                            .clip(RoundedCornerShape(24.dp))
                                            .clickable { onCategoryClick(cat.id.toString()) }
                                    ) {
                                        AsyncImage(
                                            model = cat.imageUrl,
                                            contentDescription = cat.title,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color(0x66000000)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(cat.title,
                                                style = MaterialTheme.typography.titleLarge, color = White)
                                        }
                                    }
                                }
                                // Fill empty slot if odd number
                                if (row.size < 2) Spacer(Modifier.weight(1f))
                            }
                            Spacer(Modifier.height(12.dp))
                        }
                    }
                }

                // Popular recipes horizontal scroll
                item {
                    val listState = rememberLazyListState()
                    val scope = rememberCoroutineScope()
                    val cardWidthPx = 164 // 152dp card + 12dp spacing
                    val totalItems = vm.allRecipes.size

                    // Scroll progress for indicator bar
                    val firstVisible = listState.firstVisibleItemIndex
                    val firstOffset = listState.firstVisibleItemScrollOffset
                    val progress = if (totalItems <= 1) 1f else
                        (firstVisible + firstOffset / (cardWidthPx * 3f)) / (totalItems - 1).toFloat()

                    Column {
                        // Header row with arrow buttons
                        Row(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Popular Recipes",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Gray900,
                                modifier = Modifier.weight(1f)
                            )
                            // Left arrow
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        val target = (listState.firstVisibleItemIndex - 1).coerceAtLeast(0)
                                        listState.animateScrollToItem(target)
                                    }
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Gray100, RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    Icons.Filled.ChevronLeft, null,
                                    tint = if (firstVisible > 0) Gray900 else Gray400,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(Modifier.width(6.dp))
                            // Right arrow
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        val target = (listState.firstVisibleItemIndex + 1).coerceAtMost(totalItems - 1)
                                        listState.animateScrollToItem(target)
                                    }
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Orange500, RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    Icons.Filled.ChevronRight, null,
                                    tint = White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        LazyRow(
                            state = listState,
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(vm.allRecipes) { recipe ->
                                Card(
                                    modifier = Modifier
                                        .width(152.dp)
                                        .clickable { onRecipeClick(recipe.id.toString()) },
                                    shape = RoundedCornerShape(24.dp),
                                    colors = CardDefaults.cardColors(containerColor = White),
                                    elevation = CardDefaults.cardElevation(2.dp)
                                ) {
                                    AsyncImage(
                                        model = recipe.thumbnail,
                                        contentDescription = recipe.title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(112.dp)
                                    )
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            recipe.title,
                                            style = MaterialTheme.typography.labelLarge,
                                            color = Gray900, maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(Modifier.height(4.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                Icons.Filled.AccessTime, null,
                                                tint = Orange400, modifier = Modifier.size(12.dp)
                                            )
                                            Spacer(Modifier.width(3.dp))
                                            Text(
                                                recipe.prepTime,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Gray400
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Scroll progress bar
                        Spacer(Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Gray100)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Orange500)
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
