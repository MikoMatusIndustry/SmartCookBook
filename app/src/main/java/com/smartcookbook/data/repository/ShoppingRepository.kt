package com.smartcookbook.data.repository

import com.smartcookbook.data.local.AppDatabase
import com.smartcookbook.data.local.ShoppingItemEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class ShoppingRepository(db: AppDatabase) {

    private val dao = db.shoppingItemDao()

    fun getAllItems(): Flow<List<ShoppingItemEntity>> = dao.getAllItems()

    suspend fun addItem(name: String) =
        dao.insertItem(ShoppingItemEntity(name = name))

    suspend fun toggleItem(item: ShoppingItemEntity) =
        dao.updateItem(item.copy(isChecked = !item.isChecked))

    suspend fun removeItem(item: ShoppingItemEntity) =
        dao.deleteItem(item)

    suspend fun deleteChecked() =
        dao.deleteCheckedItems()
}
