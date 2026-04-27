package com.smartcookbook.data.repository

import com.smartcookbook.data.SeedData
import com.smartcookbook.data.local.AppDatabase
import com.smartcookbook.data.local.FavoriteEntity
import com.smartcookbook.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow

class RecipeRepository(db: AppDatabase, context: Context) {

    private val favoriteDao = db.favoriteDao()
    private val prefs = context.getSharedPreferences("recent_recipes", Context.MODE_PRIVATE)

    private val _recentRecipeIds = MutableStateFlow(loadRecentIds())

    val recentRecipes: Flow<List<Recipe>> = _recentRecipeIds.map { ids ->
        val recipes = ids.mapNotNull { getRecipeById(it) }
        if (recipes.isEmpty()) SeedData.RECIPES.take(5) else recipes
    }

    fun addRecentRecipe(id: Int) {
        val current = _recentRecipeIds.value.toMutableList()
        current.remove(id)
        current.add(0, id)
        val updated = current.take(5)
        _recentRecipeIds.value = updated
        prefs.edit().putString("ids", updated.joinToString(",")).apply()
    }

    private fun loadRecentIds(): List<Int> {
        val str = prefs.getString("ids", "") ?: ""
        if (str.isBlank()) return emptyList()
        return str.split(",").mapNotNull { it.toIntOrNull() }
    }

    fun getAllRecipes(): List<Recipe> = SeedData.RECIPES

    fun getRecipeById(id: Int): Recipe? = SeedData.RECIPES.find { it.id == id }

    fun getRecipesByCategory(categoryId: Int): List<Recipe> =
        SeedData.RECIPES.filter { it.categoryId == categoryId }

    fun getFavoriteIds(): Flow<List<Int>> =
        favoriteDao.getAllFavorites().map { list -> list.map { it.recipeId } }

    fun getFavoriteRecipes(): Flow<List<Recipe>> =
        favoriteDao.getAllFavorites().map { entities ->
            val ids = entities.map { it.recipeId }
            SeedData.RECIPES.filter { it.id in ids }
        }

    fun isFavorite(recipeId: Int): Flow<Boolean> =
        favoriteDao.isFavorite(recipeId)

    suspend fun addFavorite(recipeId: Int) =
        favoriteDao.addFavorite(FavoriteEntity(recipeId = recipeId))

    suspend fun removeFavorite(recipeId: Int) {
        val favorite = favoriteDao.getFavorite(recipeId)
        if (favorite != null) {
            favoriteDao.removeFavorite(favorite)
        }
    }
}
