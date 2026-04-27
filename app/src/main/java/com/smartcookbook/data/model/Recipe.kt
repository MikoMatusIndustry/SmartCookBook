package com.smartcookbook.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Kategorie")
data class Category(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "nazwa") val title: String,
    @ColumnInfo(name = "obrazek_url") val imageUrl: String
)

@Entity(tableName = "Przepisy")
data class Recipe(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "kategoria_id", index = true) val categoryId: Int,
    @ColumnInfo(name = "nazwa") val title: String,
    @ColumnInfo(name = "czas_przygotowania") val prepTime: String,
    @ColumnInfo(name = "instrukcje") val instructions: String,
    @ColumnInfo(name = "obrazek_url") val thumbnail: String,
    @ColumnInfo(name = "wideo_url") val videoUrl: String? = null
)

@Entity(tableName = "Skladniki")
data class Ingredient(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "przepis_id", index = true) val recipeId: Int,
    @ColumnInfo(name = "nazwa") val name: String,
    @ColumnInfo(name = "ilosc") val amount: String
)
