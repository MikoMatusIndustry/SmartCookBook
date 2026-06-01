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
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun shoppingItemDao(): ShoppingItemDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        /**
         * Increment this constant whenever SeedData changes (new recipes,
         * updated videos, etc.).  The callback will then automatically clear
         * and re-insert all seed data WITHOUT touching user data
         * (favourites and shopping list are left intact).
         */
        private const val SEED_DATA_VERSION = 4
        private const val PREFS_NAME        = "smartcookbook_prefs"
        private const val KEY_SEED_VERSION  = "seed_data_version"

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                lateinit var db: AppDatabase
                db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smartcookbook_db"
                )
                .fallbackToDestructiveMigration()
                .addCallback(SeedDataCallback(context.applicationContext) { db })
                .build()
                INSTANCE = db
                db
            }

        private class SeedDataCallback(
            private val context: Context,
            private val getDb: () -> AppDatabase
        ) : Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    val storedVersion = prefs.getInt(KEY_SEED_VERSION, 0)

                    if (storedVersion != SEED_DATA_VERSION) {
                        // Seed data changed — refresh only recipe content.
                        // User data (Ulubione, Lista_Zakupow) is NOT touched.
                        val dao = getDb().recipeDao()
                        dao.deleteAllIngredients()
                        dao.deleteAllRecipes()
                        dao.deleteAllCategories()
                        dao.insertCategories(SeedData.CATEGORIES)
                        dao.insertRecipes(SeedData.RECIPES)
                        dao.insertIngredients(SeedData.INGREDIENTS)

                        prefs.edit()
                            .putInt(KEY_SEED_VERSION, SEED_DATA_VERSION)
                            .apply()
                    }
                }
            }
        }
    }
}
