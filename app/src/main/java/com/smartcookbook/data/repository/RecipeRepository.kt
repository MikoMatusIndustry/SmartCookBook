package com.smartcookbook.data.repository

import com.smartcookbook.data.SeedData
import com.smartcookbook.data.local.AppDatabase
import com.smartcookbook.data.local.FavoriteEntity
import com.smartcookbook.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeRepository(db: AppDatabase) {

    private val favoriteDao = db.favoriteDao()

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
