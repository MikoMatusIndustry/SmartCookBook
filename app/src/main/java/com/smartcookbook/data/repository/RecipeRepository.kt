package com.smartcookbook.data.repository

import android.content.Context
import com.smartcookbook.data.local.AppDatabase
import com.smartcookbook.data.local.FavoriteEntity
import com.smartcookbook.data.model.Category
import com.smartcookbook.data.model.Ingredient
import com.smartcookbook.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RecipeRepository(db: AppDatabase, context: Context) {

    private val recipeDao = db.recipeDao()
    private val favoriteDao = db.favoriteDao()
    private val prefs = context.getSharedPreferences("recent_recipes", Context.MODE_PRIVATE)

    private val _recentRecipeIds = MutableStateFlow(loadRecentIds())

    // ── Kategorie ────────────────────────────────────────────────────────────

    fun getAllCategories(): Flow<List<Category>> = recipeDao.getAllCategories()

    suspend fun getCategoryById(id: Int): Category? = recipeDao.getCategoryById(id)

    // ── Przepisy ─────────────────────────────────────────────────────────────

    fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()

    suspend fun getRecipeById(id: Int): Recipe? = recipeDao.getRecipeById(id)

    fun getRecipesByCategory(categoryId: Int): Flow<List<Recipe>> =
        recipeDao.getRecipesByCategory(categoryId)

    // ── Składniki ────────────────────────────────────────────────────────────

    fun getIngredientsByRecipeId(recipeId: Int): Flow<List<Ingredient>> =
        recipeDao.getIngredientsByRecipeId(recipeId)

    // ── Ostatnio przeglądane ─────────────────────────────────────────────────

    val recentRecipes: Flow<List<Recipe>> = _recentRecipeIds.flatMapLatest { ids ->
        if (ids.isEmpty()) {
            // Fallback: pokaż pierwsze 5 przepisów z bazy
            recipeDao.getAllRecipes().map { it.take(5) }
        } else {
            recipeDao.getRecipesByIds(ids).map { recipes ->
                // Zachowaj kolejność z listy IDs
                ids.mapNotNull { id -> recipes.find { it.id == id } }
            }
        }
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

    // ── Ulubione ─────────────────────────────────────────────────────────────

    fun getFavoriteIds(): Flow<List<Int>> =
        favoriteDao.getAllFavorites().map { list -> list.map { it.recipeId } }

    fun getFavoriteRecipes(): Flow<List<Recipe>> =
        favoriteDao.getAllFavorites().flatMapLatest { entities ->
            val ids = entities.map { it.recipeId }
            if (ids.isEmpty()) flowOf(emptyList())
            else recipeDao.getRecipesByIds(ids)
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
