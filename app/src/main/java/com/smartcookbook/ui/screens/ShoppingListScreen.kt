package com.smartcookbook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.smartcookbook.data.local.ShoppingItemEntity
import com.smartcookbook.ui.components.BottomNavBar
import com.smartcookbook.ui.components.BottomNavTab
import com.smartcookbook.ui.theme.*
import com.smartcookbook.viewmodel.ShoppingListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    vm: ShoppingListViewModel,
    onBack: () -> Unit,
    onHomeClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    val items by vm.items.collectAsState()
    var newItemText by remember { mutableStateOf("") }
    val checkedCount = items.count { it.isChecked }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Grocery List", style = MaterialTheme.typography.headlineSmall, color = Gray900)
                        if (items.isNotEmpty())
                            Text("$checkedCount of ${items.size} done",
                                style = MaterialTheme.typography.labelMedium, color = Gray400)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
            )
        },
        bottomBar = {
            BottomNavBar(
                currentTab = BottomNavTab.SHOPPING,
                onHomeClick = onHomeClick,
                onFavoritesClick = onFavoritesClick,
                onShoppingClick = {}
            )
        },
        containerColor = Gray50
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Add item row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newItemText,
                    onValueChange = { newItemText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Add an item...", color = Gray400) },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Orange500,
                        unfocusedBorderColor = Gray200,
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    )
                )
                IconButton(
                    onClick = {
                        if (newItemText.isNotBlank()) {
                            vm.addItem(newItemText.trim())
                            newItemText = ""
                        }
                    },
                    modifier = Modifier
                        .size(52.dp)
                        .background(Gray900, RoundedCornerShape(16.dp))
                ) {
                    Icon(Icons.Filled.Add, "Add", tint = White)
                }
            }

            // Progress bar
            if (items.isNotEmpty()) {
                LinearProgressIndicator(
                    progress = { if (items.isEmpty()) 0f else checkedCount.toFloat() / items.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(50))
                        .height(6.dp),
                    color = Orange400,
                    trackColor = Gray200
                )
                Spacer(Modifier.height(8.dp))
            }

            // List
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                if (items.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Filled.ShoppingBag, null, tint = Gray200,
                            modifier = Modifier.size(40.dp))
                        Spacer(Modifier.height(8.dp))
                        Text("Your list is empty", style = MaterialTheme.typography.bodyMedium, color = Gray400)
                    }
                } else {
                    LazyColumn {
                        items(items, key = { it.id }) { item ->
                            ShoppingItemRow(
                                item = item,
                                onToggle = { vm.toggleItem(item) },
                                onDelete = { vm.removeItem(item) }
                            )
                            HorizontalDivider(color = Gray100)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ShoppingItemRow(
    item: ShoppingItemEntity,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = { onToggle() },
            colors = CheckboxDefaults.colors(
                checkedColor = Orange500,
                uncheckedColor = Gray400,
                checkmarkColor = White
            )
        )
        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                textDecoration = if (item.isChecked) TextDecoration.LineThrough else null,
                color = if (item.isChecked) Gray400 else Gray800
            ),
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onDelete) {
            Icon(Icons.Filled.Delete, "Delete", tint = Gray400)
        }
    }
}
