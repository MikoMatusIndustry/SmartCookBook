package com.smartcookbook.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.smartcookbook.data.model.Recipe

@Entity(tableName = "Ulubione")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "user_uid") val userUid: String = "default_user",
    @ColumnInfo(name = "przepis_id", index = true) val recipeId: Int
)
