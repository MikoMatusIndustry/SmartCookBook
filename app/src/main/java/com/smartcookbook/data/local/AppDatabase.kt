package com.smartcookbook.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.smartcookbook.data.SeedData
import com.smartcookbook.data.model.Category
import com.smartcookbook.data.model.Ingredient
import com.smartcookbook.data.model.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Category::class,
        Recipe::class,
        Ingredient::class,
        FavoriteEntity::class,
        ShoppingItemEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun shoppingItemDao(): ShoppingItemDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                lateinit var db: AppDatabase
                db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smartcookbook_db"
                )
                .fallbackToDestructiveMigration()
                .addCallback(PrepopulateCallback { db })
                .build()
                INSTANCE = db
                db
            }

        private class PrepopulateCallback(private val getDb: () -> AppDatabase) : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = getDb().recipeDao()
                    if (dao.getRecipeCount() == 0) {
                        dao.insertCategories(SeedData.CATEGORIES)
                        dao.insertRecipes(SeedData.RECIPES)
                        dao.insertIngredients(SeedData.INGREDIENTS)
                    }
                }
            }
        }
    }
}
