package com.smartcookbook.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartcookbook.ui.theme.*

enum class BottomNavTab { HOME, FAVORITES, SHOPPING }

@Composable
fun BottomNavBar(
    currentTab: BottomNavTab,
    onHomeClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onShoppingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = White,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = currentTab == BottomNavTab.HOME,
            onClick = onHomeClick,
            icon = {
                Icon(
                    imageVector = if (currentTab == BottomNavTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home", style = MaterialTheme.typography.labelSmall) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Orange500,
                selectedTextColor = Orange500,
                indicatorColor = Orange100,
                unselectedIconColor = Gray400,
                unselectedTextColor = Gray400
            )
        )
        NavigationBarItem(
            selected = currentTab == BottomNavTab.FAVORITES,
            onClick = onFavoritesClick,
            icon = {
                Icon(
                    imageVector = if (currentTab == BottomNavTab.FAVORITES) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorites"
                )
            },
            label = { Text("Favorites", style = MaterialTheme.typography.labelSmall) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Orange500,
                selectedTextColor = Orange500,
                indicatorColor = Orange100,
                unselectedIconColor = Gray400,
                unselectedTextColor = Gray400
            )
        )
        NavigationBarItem(
            selected = currentTab == BottomNavTab.SHOPPING,
            onClick = onShoppingClick,
            icon = {
                Icon(
                    imageVector = if (currentTab == BottomNavTab.SHOPPING) Icons.Filled.ShoppingBag else Icons.Outlined.ShoppingBag,
                    contentDescription = "List"
                )
            },
            label = { Text("List", style = MaterialTheme.typography.labelSmall) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Orange500,
                selectedTextColor = Orange500,
                indicatorColor = Orange100,
                unselectedIconColor = Gray400,
                unselectedTextColor = Gray400
            )
        )
    }
}
