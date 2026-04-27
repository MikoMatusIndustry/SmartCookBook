package com.smartcookbook.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM Ulubione")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun removeFavorite(favorite: FavoriteEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM Ulubione WHERE przepis_id = :recipeId LIMIT 1)")
    fun isFavorite(recipeId: Int): Flow<Boolean>

    @Query("SELECT * FROM Ulubione WHERE przepis_id = :recipeId LIMIT 1")
    suspend fun getFavorite(recipeId: Int): FavoriteEntity?
}
