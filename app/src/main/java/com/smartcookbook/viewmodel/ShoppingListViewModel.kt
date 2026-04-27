package com.smartcookbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.smartcookbook.data.local.ShoppingItemEntity
import com.smartcookbook.data.repository.ShoppingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShoppingListViewModel(private val repo: ShoppingRepository) : ViewModel() {

    val items: StateFlow<List<ShoppingItemEntity>> = repo.getAllItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addItem(name: String) = viewModelScope.launch { repo.addItem(name) }
    fun toggleItem(item: ShoppingItemEntity) = viewModelScope.launch { repo.toggleItem(item) }
    fun removeItem(item: ShoppingItemEntity) = viewModelScope.launch { repo.removeItem(item) }
    fun deleteChecked() = viewModelScope.launch { repo.deleteChecked() }

    class Factory(private val repo: ShoppingRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = ShoppingListViewModel(repo) as T
    }
}
