package com.smartcookbook.data.local

import androidx.room.*
import com.smartcookbook.data.model.Category
import com.smartcookbook.data.model.Ingredient
import com.smartcookbook.data.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    // ── Kategorie ────────────────────────────────────────────────────────────

    @Query("SELECT * FROM Kategorie")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM Kategorie WHERE id = :id")
    suspend fun getCategoryById(id: Int): Category?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)

    // ── Przepisy ─────────────────────────────────────────────────────────────

    @Query("SELECT * FROM Przepisy")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM Przepisy WHERE id = :id")
    suspend fun getRecipeById(id: Int): Recipe?

    @Query("SELECT * FROM Przepisy WHERE kategoria_id = :categoryId")
    fun getRecipesByCategory(categoryId: Int): Flow<List<Recipe>>

    @Query("SELECT * FROM Przepisy WHERE id IN (:ids)")
    fun getRecipesByIds(ids: List<Int>): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Query("SELECT COUNT(*) FROM Przepisy")
    suspend fun getRecipeCount(): Int

    // ── Składniki ────────────────────────────────────────────────────────────

    @Query("SELECT * FROM Skladniki WHERE przepis_id = :recipeId")
    fun getIngredientsByRecipeId(recipeId: Int): Flow<List<Ingredient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<Ingredient>)
}
