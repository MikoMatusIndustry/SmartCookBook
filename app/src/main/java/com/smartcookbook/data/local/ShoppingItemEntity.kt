package com.smartcookbook.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Lista_Zakupow")
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "user_uid") val userUid: String = "default_user",
    @ColumnInfo(name = "nazwa_produktu") val name: String,
    @ColumnInfo(name = "czy_kupione") val isChecked: Boolean = false
)
