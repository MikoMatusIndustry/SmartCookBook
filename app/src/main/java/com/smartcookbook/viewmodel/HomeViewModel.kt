package com.smartcookbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.smartcookbook.data.SeedData
import com.smartcookbook.data.model.Recipe
import com.smartcookbook.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(private val repo: RecipeRepository) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val categories = SeedData.CATEGORIES
    val recipeOfDay = SeedData.RECIPES.first()
    val allRecipes  = SeedData.RECIPES

    val recentRecipes: StateFlow<List<Recipe>> = repo.recentRecipes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteIds: StateFlow<List<Int>> = repo.getFavoriteIds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val searchResults: StateFlow<List<Recipe>> =
        combine(searchQuery, MutableStateFlow(SeedData.RECIPES)) { query, recipes ->
            if (query.isBlank()) emptyList()
            else recipes.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    class Factory(private val repo: RecipeRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = HomeViewModel(repo) as T
    }
}
