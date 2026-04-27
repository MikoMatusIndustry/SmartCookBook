package com.smartcookbook.data

import com.smartcookbook.data.model.Category
import com.smartcookbook.data.model.Recipe
import com.smartcookbook.data.model.Ingredient

object SeedData {

    val CATEGORIES = listOf(
        Category(1, "Breakfast", "https://images.unsplash.com/photo-1610015644714-60076be69b30?w=800"),
        Category(2, "Lunch", "https://images.unsplash.com/photo-1649531794884-b8bb1de72e68?w=800"),
        Category(3, "Dinner", "https://images.unsplash.com/photo-1706650616334-97875fae8521?w=800"),
        Category(4, "Desserts", "https://images.unsplash.com/photo-1700448293876-07dca826c161?w=800"),
    )

    val RECIPES = listOf(
        Recipe(
            id = 1, title = "Gourmet Pasta Primavera",
            thumbnail = "https://images.unsplash.com/photo-1762631178597-847861217da0?w=800",
            videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4",
            prepTime = "25 min",
            categoryId = 3,
            instructions = "Boil water and cook pasta according to package instructions.\nSaute mixed vegetables with garlic in olive oil over medium heat.\nDrain pasta and combine with vegetables.\nTop generously with Parmesan cheese and fresh basil."
        ),
        Recipe(
            id = 2, title = "Avocado Toast & Egg",
            thumbnail = "https://images.unsplash.com/photo-1585819531730-06d1aba54ce1?w=800",
            videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4",
            prepTime = "10 min",
            categoryId = 1,
            instructions = "Toast the bread until golden brown and crispy.\nMash the avocado with lemon juice, salt, and pepper.\nSpread avocado evenly on the toast.\nFry or poach the eggs and place on top.\nSeason with chili flakes."
        )
    )

    val INGREDIENTS = listOf(
        Ingredient(recipeId = 1, name = "pasta", amount = "1 lb"),
        Ingredient(recipeId = 1, name = "mixed vegetables", amount = "2 cups"),
        Ingredient(recipeId = 1, name = "olive oil", amount = "1/4 cup"),
        Ingredient(recipeId = 1, name = "garlic cloves", amount = "3"),
        Ingredient(recipeId = 2, name = "sourdough bread", amount = "2 slices"),
        Ingredient(recipeId = 2, name = "ripe avocado", amount = "1"),
        Ingredient(recipeId = 2, name = "large eggs", amount = "2")
    )
}
