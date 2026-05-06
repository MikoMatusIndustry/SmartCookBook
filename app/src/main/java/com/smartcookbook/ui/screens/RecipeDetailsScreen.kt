package com.smartcookbook.ui.screens

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil3.compose.AsyncImage

import com.smartcookbook.ui.theme.*
import com.smartcookbook.viewmodel.RecipeDetailsViewModel
import com.smartcookbook.viewmodel.ShoppingListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    vm: RecipeDetailsViewModel,
    shoppingVm: ShoppingListViewModel,
    onBack: () -> Unit,
    onTimerClick: () -> Unit
) {
    val recipe by vm.recipe.collectAsState()
    val currentRecipe = recipe ?: run {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Recipe not found")
        }
        return
    }

    val isFavorite by vm.isFavorite.collectAsState()
    val ingredients by vm.ingredients.collectAsState()
    var activeTab by remember { mutableStateOf(0) } // 0=Ingredients, 1=Instructions
    var showVideo by remember { mutableStateOf(false) }
    val addedIngredients = remember { mutableStateSetOf<String>() }
    val context = LocalContext.current

    // ExoPlayer for video
    val exoPlayer = remember(currentRecipe.videoUrl) {
        if (currentRecipe.videoUrl != null) {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(currentRecipe.videoUrl))
                prepare()
            }
        } else null
    }
    DisposableEffect(exoPlayer) { onDispose { exoPlayer?.release() } }

    Scaffold(containerColor = White) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Hero image / video (top 280dp)
                Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
                    if (showVideo && exoPlayer != null) {
                        AndroidView(
                            factory = { ctx ->
                                PlayerView(ctx).apply {
                                    player = exoPlayer
                                    exoPlayer.playWhenReady = true
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                        IconButton(
                            onClick = { showVideo = false; exoPlayer.pause() },
                            modifier = Modifier.align(Alignment.TopStart).padding(8.dp)
                                .background(Color(0x88000000), CircleShape)
                        ) {
                            Icon(Icons.Filled.ArrowBack, "Back", tint = White)
                        }
                    } else {
                        AsyncImage(
                            model = currentRecipe.thumbnail,
                            contentDescription = currentRecipe.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .background(Brush.verticalGradient(
                                    colors = listOf(Color(0x66000000), Color.Transparent, Color(0x99111827)),
                                    startY = 0f, endY = Float.POSITIVE_INFINITY
                                ))
                        )
                        // Play button
                        if (currentRecipe.videoUrl != null) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                IconButton(
                                    onClick = { showVideo = true },
                                    modifier = Modifier
                                        .size(64.dp)
                                        .background(Color(0x44FFFFFF), CircleShape)
                                ) {
                                    Icon(Icons.Filled.PlayArrow, "Play", tint = White,
                                        modifier = Modifier.size(36.dp))
                                }
                            }
                        }
                        // Top nav row
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(12.dp).statusBarsPadding(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = onBack,
                                modifier = Modifier.background(Color(0x44FFFFFF), CircleShape)
                            ) { Icon(Icons.Filled.ArrowBack, "Back", tint = White) }
                            IconButton(
                                onClick = { vm.toggleFavorite() },
                                modifier = Modifier.background(Color(0x44FFFFFF), CircleShape)
                            ) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (isFavorite) Orange500 else White
                                )
                            }
                        }
                    }
                }

                // Content scrollable sheet
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = (-24).dp)
                        .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                        .background(White)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                        .padding(bottom = 80.dp)
                ) {
                    Text(currentRecipe.title, style = MaterialTheme.typography.headlineLarge, color = Gray900)
                    Spacer(Modifier.height(8.dp))

                    // Meta chips
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.horizontalScroll(rememberScrollState())) {
                        Surface(shape = RoundedCornerShape(50), color = Orange50) {
                            Row(Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.AccessTime, null, tint = Orange500, modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(3.dp))
                                Text(currentRecipe.prepTime, style = MaterialTheme.typography.labelLarge, color = Gray700)
                            }
                        }
                    }


                    Spacer(Modifier.height(20.dp))

                    // Tabs
                    TabRow(
                        selectedTabIndex = activeTab,
                        containerColor = White,
                        contentColor = Orange500,
                        divider = { HorizontalDivider(color = Gray100) }
                    ) {
                        Tab(selected = activeTab == 0, onClick = { activeTab = 0 },
                            text = { Text("Ingredients", style = MaterialTheme.typography.labelLarge) })
                        Tab(selected = activeTab == 1, onClick = { activeTab = 1 },
                            text = { Text("Instructions", style = MaterialTheme.typography.labelLarge) })
                    }

                    Spacer(Modifier.height(16.dp))

                    if (activeTab == 0) {
                        ingredients.forEach { ingredientObj ->
                            val ingredient = ingredientObj.name + " " + ingredientObj.amount
                            val added = ingredient in addedIngredients
                            Surface(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                shape = RoundedCornerShape(16.dp),
                                color = Gray50,
                                border = BorderStroke(1.dp, Gray100)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(ingredient, style = MaterialTheme.typography.bodyMedium,
                                        color = Gray800, modifier = Modifier.weight(1f))
                                    IconButton(
                                        onClick = {
                                            if (!added) {
                                                shoppingVm.addItem(ingredientObj.name)
                                                addedIngredients.add(ingredient)
                                            }
                                        },
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(if (added) Green100 else Orange100, CircleShape)
                                    ) {
                                        Icon(
                                            imageVector = if (added) Icons.Filled.CheckCircle else Icons.Filled.Restaurant,
                                            contentDescription = "Add",
                                            tint = if (added) Green500 else Orange500,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        val steps = currentRecipe.instructions.split("\n").filter { it.isNotBlank() }
                        steps.forEachIndexed { idx, step ->
                            Row(
                                modifier = Modifier.padding(vertical = 8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = Orange100,
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                        Text("${idx + 1}",
                                            style = MaterialTheme.typography.labelLarge, color = Orange500)
                                    }
                                }
                                Spacer(Modifier.width(12.dp))
                                Text(step, style = MaterialTheme.typography.bodyMedium,
                                    color = Gray700, modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            // FAB for timer
            FloatingActionButton(
                onClick = onTimerClick,
                modifier = Modifier.align(Alignment.BottomEnd).padding(20.dp),
                shape = RoundedCornerShape(20.dp),
                containerColor = Gray900,
                contentColor = White
            ) {
                Icon(Icons.Filled.Timer, "Timer")
            }
        }
    }
}

private fun <E> mutableStateSetOf(vararg elements: E): MutableSet<E> =
    mutableSetOf(*elements)
