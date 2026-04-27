package com.smartcookbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.smartcookbook.data.model.Recipe
import com.smartcookbook.data.repository.RecipeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repo: RecipeRepository) : ViewModel() {

    val favoriteRecipes: StateFlow<List<Recipe>> = repo.getFavoriteRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeFavorite(recipeId: Int) = viewModelScope.launch {
        repo.removeFavorite(recipeId)
    }

    class Factory(private val repo: RecipeRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = FavoritesViewModel(repo) as T
    }
}
