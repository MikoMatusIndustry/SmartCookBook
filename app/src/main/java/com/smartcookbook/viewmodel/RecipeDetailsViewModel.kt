package com.smartcookbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.smartcookbook.data.model.Ingredient
import com.smartcookbook.data.model.Recipe
import com.smartcookbook.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    private val repo: RecipeRepository,
    val recipeId: Int
) : ViewModel() {

    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe

    val ingredients: StateFlow<List<Ingredient>> = repo.getIngredientsByRecipeId(recipeId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            _recipe.value = repo.getRecipeById(recipeId)
        }
        repo.addRecentRecipe(recipeId)
    }

    val isFavorite: StateFlow<Boolean> = repo.isFavorite(recipeId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun toggleFavorite() = viewModelScope.launch {
        if (isFavorite.value) repo.removeFavorite(recipeId)
        else repo.addFavorite(recipeId)
    }

    class Factory(private val repo: RecipeRepository, private val recipeId: Int) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            RecipeDetailsViewModel(repo, recipeId) as T
    }
}
