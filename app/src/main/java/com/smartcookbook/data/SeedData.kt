package com.smartcookbook.data

import com.smartcookbook.data.model.Category
import com.smartcookbook.data.model.Recipe
import com.smartcookbook.data.model.Ingredient

object SeedData {

    val CATEGORIES = listOf(
        Category(1, "Breakfast", "https://images.unsplash.com/photo-1533089860892-a7c6f0a88666?w=800"),
        Category(2, "Lunch",     "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=800"),
        Category(3, "Dinner",    "https://images.unsplash.com/photo-1544025162-d76538b2a6d5?w=800"),
        Category(4, "Desserts",  "https://images.unsplash.com/photo-1551024601-bec78aea704b?w=800"),
    )

    val RECIPES = listOf(
        // ── BREAKFAST ──────────────────────────────────────────────────────────
        Recipe(
            id = 1, categoryId = 1,
            title = "Avocado Toast & Poached Egg",
            prepTime = "10 min",
            thumbnail = "https://images.unsplash.com/photo-1525351484163-7529414344d8?w=800",
            videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4",
            instructions = "Toast sourdough bread slices until golden and crispy.\nMash ripe avocado with lemon juice, salt, pepper and chili flakes.\nPoach eggs in simmering water with a splash of vinegar for 3 minutes.\nSpread avocado on toast and top with the poached egg.\nFinish with extra chili flakes and fresh dill."
        ),
        Recipe(
            id = 2, categoryId = 1,
            title = "Fluffy Blueberry Pancakes",
            prepTime = "20 min",
            thumbnail = "https://images.unsplash.com/photo-1528207776546-365bb710ee93?w=800",
            videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4",
            instructions = "Mix flour, baking powder, sugar and salt in a bowl.\nWhisk together milk, egg and melted butter, then combine with dry ingredients.\nFold in fresh blueberries gently.\nPour 1/4 cup portions onto a hot greased pan.\nCook until bubbles form, flip, cook 1 more minute.\nServe with maple syrup and extra blueberries."
        ),
        Recipe(
            id = 3, categoryId = 1,
            title = "Greek Yogurt Parfait",
            prepTime = "5 min",
            thumbnail = "https://images.unsplash.com/photo-1488477181946-6428a0291777?w=800",
            videoUrl = null,
            instructions = "Spoon a layer of Greek yogurt into a tall glass.\nAdd a layer of granola for crunch.\nTop with mixed berries — strawberries, raspberries and blueberries.\nDrizzle honey over the top.\nRepeat layers and finish with a mint leaf."
        ),
        Recipe(
            id = 4, categoryId = 1,
            title = "Veggie Omelette",
            prepTime = "15 min",
            thumbnail = "https://images.unsplash.com/photo-1510693206972-df098062cb71?w=800",
            videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4",
            instructions = "Beat 3 eggs with a pinch of salt and pepper.\nSauté diced onion and bell pepper in butter for 2 minutes.\nAdd sliced mushrooms and cook until soft.\nPour egg mixture over vegetables in the pan.\nCook on low heat, fold in half when set.\nGarnish with chopped chives and serve immediately."
        ),

        // ── LUNCH ──────────────────────────────────────────────────────────────
        Recipe(
            id = 5, categoryId = 2,
            title = "Classic Caesar Salad",
            prepTime = "15 min",
            thumbnail = "https://images.unsplash.com/photo-1512852939750-1305098529bf?w=800",
            videoUrl = null,
            instructions = "Tear romaine lettuce into bite-sized pieces.\nMake dressing: whisk together garlic, Dijon mustard, lemon juice, Worcestershire sauce and olive oil.\nToss lettuce with dressing until well coated.\nAdd croutons and shaved Parmesan.\nSeason generously with black pepper and serve immediately."
        ),
        Recipe(
            id = 6, categoryId = 2,
            title = "Grilled Chicken Wrap",
            prepTime = "20 min",
            thumbnail = "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?w=800",
            videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4",
            instructions = "Season chicken breast with cumin, paprika, garlic powder, salt and pepper.\nGrill over medium-high heat for 6–7 minutes per side.\nSlice into strips and let rest.\nWarm a flour tortilla on the pan.\nLayer with lettuce, sliced tomato, chicken strips, shredded cheese and sour cream.\nRoll tightly and cut diagonally."
        ),
        Recipe(
            id = 7, categoryId = 2,
            title = "Tomato Basil Soup",
            prepTime = "30 min",
            thumbnail = "https://images.unsplash.com/photo-1547592166-23ac45744acd?w=800",
            videoUrl = null,
            instructions = "Sauté diced onion and garlic in olive oil until soft.\nAdd canned crushed tomatoes, vegetable broth and a pinch of sugar.\nSimmer for 20 minutes on medium-low heat.\nBlend the soup until smooth using an immersion blender.\nStir in heavy cream and fresh basil leaves.\nSeason with salt and pepper, serve with crusty bread."
        ),
        Recipe(
            id = 8, categoryId = 2,
            title = "Quinoa Buddha Bowl",
            prepTime = "25 min",
            thumbnail = "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=800",
            videoUrl = null,
            instructions = "Cook quinoa in vegetable broth for extra flavour.\nRoast chickpeas with olive oil, cumin and paprika at 200°C for 20 minutes.\nSlice avocado, cucumber and cherry tomatoes.\nAssemble bowl with quinoa base, then arrange all toppings.\nDrizzle with tahini sauce and sprinkle sesame seeds.\nFinish with a squeeze of lemon juice."
        ),

        // ── DINNER ─────────────────────────────────────────────────────────────
        Recipe(
            id = 9, categoryId = 3,
            title = "Gourmet Pasta Primavera",
            prepTime = "25 min",
            thumbnail = "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=800",
            videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4",
            instructions = "Boil salted water and cook pasta al dente.\nSauté garlic in olive oil, add cherry tomatoes and zucchini.\nAdd asparagus and peas, cook for 3 more minutes.\nToss drained pasta with the vegetables and pasta water.\nFinish with Parmesan, lemon zest and fresh basil.\nSeason generously and serve immediately."
        ),
        Recipe(
            id = 10, categoryId = 3,
            title = "Beef Stir-Fry",
            prepTime = "20 min",
            thumbnail = "https://images.unsplash.com/photo-1603360946369-dc9bb6258143?w=800",
            videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4",
            instructions = "Slice beef sirloin thinly against the grain.\nMarinate in soy sauce, sesame oil and cornstarch for 10 minutes.\nHeat wok until smoking, stir-fry beef until browned, set aside.\nStir-fry broccoli, bell peppers and snap peas.\nReturn beef and add oyster sauce and beef broth.\nServe over steamed white rice garnished with sesame seeds."
        ),
        Recipe(
            id = 11, categoryId = 3,
            title = "Baked Lemon Salmon",
            prepTime = "30 min",
            thumbnail = "https://images.unsplash.com/photo-1467003909585-2f8a72700288?w=800",
            videoUrl = null,
            instructions = "Preheat oven to 200°C.\nPlace salmon fillets on a lined baking sheet.\nRub with olive oil, minced garlic, lemon zest, salt and pepper.\nTop with thin lemon slices and fresh dill sprigs.\nBake for 12–15 minutes until salmon flakes easily.\nServe with roasted asparagus and a wedge of lemon."
        ),
        Recipe(
            id = 12, categoryId = 3,
            title = "Chicken Tikka Masala",
            prepTime = "45 min",
            thumbnail = "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=800",
            videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4",
            instructions = "Marinate chicken in yogurt, garam masala, cumin and turmeric for 30 minutes.\nGrill or pan-fry chicken until charred, then cut into chunks.\nSauté onion, ginger and garlic until golden.\nAdd tomato puree, cumin, coriander and chili powder.\nPour in heavy cream and simmer for 10 minutes.\nAdd chicken and cook for another 5 minutes. Serve with basmati rice and naan."
        ),

        // ── DESSERTS ───────────────────────────────────────────────────────────
        Recipe(
            id = 13, categoryId = 4,
            title = "Classic Crème Brûlée",
            prepTime = "60 min",
            thumbnail = "https://images.unsplash.com/photo-1470124182917-cc6e71b22ecc?w=800",
            videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4",
            instructions = "Preheat oven to 160°C.\nHeat heavy cream with vanilla bean until just simmering.\nWhisk egg yolks with sugar until pale and creamy.\nSlowly pour warm cream into yolks, whisking constantly.\nStrain and pour into ramekins. Place in a water bath.\nBake for 40 minutes until set but slightly jiggly.\nChill for 2 hours, then sprinkle sugar on top and torch until caramelised."
        ),
        Recipe(
            id = 14, categoryId = 4,
            title = "Chocolate Lava Cake",
            prepTime = "25 min",
            thumbnail = "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=800",
            videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4",
            instructions = "Preheat oven to 220°C. Grease and flour 4 ramekins.\nMelt dark chocolate and butter together over a double boiler.\nWhisk together eggs, egg yolks and sugar until thick.\nFold chocolate mixture into egg mixture.\nFold in flour until just combined.\nPour into ramekins and bake for exactly 12 minutes.\nInvert onto plates immediately and serve with vanilla ice cream."
        ),
        Recipe(
            id = 15, categoryId = 4,
            title = "Mango Panna Cotta",
            prepTime = "20 min",
            thumbnail = "https://images.unsplash.com/photo-1488477181946-6428a0291777?w=800",
            videoUrl = null,
            instructions = "Bloom gelatin in cold water for 5 minutes.\nWarm heavy cream with sugar and vanilla until sugar dissolves.\nAdd bloomed gelatin and stir until dissolved. Remove from heat.\nPour into glasses and refrigerate for at least 4 hours.\nBlend fresh mango with a squeeze of lime for the topping.\nPour mango coulis over set panna cotta and serve chilled."
        ),
        Recipe(
            id = 16, categoryId = 4,
            title = "Strawberry Cheesecake",
            prepTime = "90 min",
            thumbnail = "https://images.unsplash.com/photo-1533134242443-d4fd215305ad?w=800",
            videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4",
            instructions = "Crush digestive biscuits and mix with melted butter. Press into a springform pan.\nBeat cream cheese with sugar until smooth.\nAdd eggs one at a time, then mix in sour cream and vanilla.\nPour over the crust and bake at 160°C for 55 minutes.\nLet cool completely, then refrigerate overnight.\nTop with fresh strawberries and strawberry glaze before serving."
        )
    )

    val INGREDIENTS = listOf(
        // Recipe 1 – Avocado Toast & Poached Egg
        Ingredient(recipeId = 1, name = "Sourdough bread", amount = "2 slices"),
        Ingredient(recipeId = 1, name = "Ripe avocado", amount = "1"),
        Ingredient(recipeId = 1, name = "Large eggs", amount = "2"),
        Ingredient(recipeId = 1, name = "Lemon juice", amount = "1 tbsp"),
        Ingredient(recipeId = 1, name = "Chili flakes", amount = "pinch"),
        Ingredient(recipeId = 1, name = "Fresh dill", amount = "a few sprigs"),

        // Recipe 2 – Fluffy Blueberry Pancakes
        Ingredient(recipeId = 2, name = "All-purpose flour", amount = "1.5 cups"),
        Ingredient(recipeId = 2, name = "Baking powder", amount = "2 tsp"),
        Ingredient(recipeId = 2, name = "Sugar", amount = "2 tbsp"),
        Ingredient(recipeId = 2, name = "Milk", amount = "1 cup"),
        Ingredient(recipeId = 2, name = "Egg", amount = "1"),
        Ingredient(recipeId = 2, name = "Melted butter", amount = "2 tbsp"),
        Ingredient(recipeId = 2, name = "Fresh blueberries", amount = "1 cup"),
        Ingredient(recipeId = 2, name = "Maple syrup", amount = "to serve"),

        // Recipe 3 – Greek Yogurt Parfait
        Ingredient(recipeId = 3, name = "Greek yogurt", amount = "1.5 cups"),
        Ingredient(recipeId = 3, name = "Granola", amount = "1/2 cup"),
        Ingredient(recipeId = 3, name = "Mixed berries", amount = "1 cup"),
        Ingredient(recipeId = 3, name = "Honey", amount = "2 tbsp"),

        // Recipe 4 – Veggie Omelette
        Ingredient(recipeId = 4, name = "Large eggs", amount = "3"),
        Ingredient(recipeId = 4, name = "Butter", amount = "1 tbsp"),
        Ingredient(recipeId = 4, name = "Bell pepper", amount = "1/2, diced"),
        Ingredient(recipeId = 4, name = "Mushrooms", amount = "100 g"),
        Ingredient(recipeId = 4, name = "Red onion", amount = "1/4, diced"),
        Ingredient(recipeId = 4, name = "Fresh chives", amount = "to garnish"),

        // Recipe 5 – Classic Caesar Salad
        Ingredient(recipeId = 5, name = "Romaine lettuce", amount = "1 head"),
        Ingredient(recipeId = 5, name = "Parmesan cheese", amount = "50 g, shaved"),
        Ingredient(recipeId = 5, name = "Croutons", amount = "1 cup"),
        Ingredient(recipeId = 5, name = "Garlic clove", amount = "1"),
        Ingredient(recipeId = 5, name = "Dijon mustard", amount = "1 tsp"),
        Ingredient(recipeId = 5, name = "Lemon juice", amount = "2 tbsp"),
        Ingredient(recipeId = 5, name = "Olive oil", amount = "3 tbsp"),

        // Recipe 6 – Grilled Chicken Wrap
        Ingredient(recipeId = 6, name = "Chicken breast", amount = "300 g"),
        Ingredient(recipeId = 6, name = "Flour tortilla", amount = "2 large"),
        Ingredient(recipeId = 6, name = "Romaine lettuce", amount = "handful"),
        Ingredient(recipeId = 6, name = "Tomato", amount = "1, sliced"),
        Ingredient(recipeId = 6, name = "Cheddar cheese", amount = "50 g, shredded"),
        Ingredient(recipeId = 6, name = "Sour cream", amount = "2 tbsp"),
        Ingredient(recipeId = 6, name = "Cumin & paprika", amount = "1 tsp each"),

        // Recipe 7 – Tomato Basil Soup
        Ingredient(recipeId = 7, name = "Crushed tomatoes", amount = "800 g (2 cans)"),
        Ingredient(recipeId = 7, name = "Vegetable broth", amount = "2 cups"),
        Ingredient(recipeId = 7, name = "Yellow onion", amount = "1, diced"),
        Ingredient(recipeId = 7, name = "Garlic cloves", amount = "3"),
        Ingredient(recipeId = 7, name = "Heavy cream", amount = "1/2 cup"),
        Ingredient(recipeId = 7, name = "Fresh basil", amount = "large handful"),
        Ingredient(recipeId = 7, name = "Olive oil", amount = "2 tbsp"),

        // Recipe 8 – Quinoa Buddha Bowl
        Ingredient(recipeId = 8, name = "Quinoa", amount = "1 cup"),
        Ingredient(recipeId = 8, name = "Canned chickpeas", amount = "400 g"),
        Ingredient(recipeId = 8, name = "Avocado", amount = "1"),
        Ingredient(recipeId = 8, name = "Cherry tomatoes", amount = "1 cup"),
        Ingredient(recipeId = 8, name = "Cucumber", amount = "1/2"),
        Ingredient(recipeId = 8, name = "Tahini", amount = "3 tbsp"),
        Ingredient(recipeId = 8, name = "Lemon juice", amount = "2 tbsp"),

        // Recipe 9 – Gourmet Pasta Primavera
        Ingredient(recipeId = 9, name = "Penne pasta", amount = "400 g"),
        Ingredient(recipeId = 9, name = "Cherry tomatoes", amount = "1 cup"),
        Ingredient(recipeId = 9, name = "Zucchini", amount = "1, sliced"),
        Ingredient(recipeId = 9, name = "Asparagus", amount = "100 g"),
        Ingredient(recipeId = 9, name = "Frozen peas", amount = "1/2 cup"),
        Ingredient(recipeId = 9, name = "Garlic cloves", amount = "3"),
        Ingredient(recipeId = 9, name = "Parmesan cheese", amount = "60 g, grated"),
        Ingredient(recipeId = 9, name = "Olive oil", amount = "3 tbsp"),

        // Recipe 10 – Beef Stir-Fry
        Ingredient(recipeId = 10, name = "Beef sirloin", amount = "400 g, thinly sliced"),
        Ingredient(recipeId = 10, name = "Broccoli florets", amount = "1.5 cups"),
        Ingredient(recipeId = 10, name = "Red bell pepper", amount = "1, sliced"),
        Ingredient(recipeId = 10, name = "Snap peas", amount = "1 cup"),
        Ingredient(recipeId = 10, name = "Soy sauce", amount = "3 tbsp"),
        Ingredient(recipeId = 10, name = "Oyster sauce", amount = "2 tbsp"),
        Ingredient(recipeId = 10, name = "Sesame oil", amount = "1 tsp"),
        Ingredient(recipeId = 10, name = "White rice", amount = "2 cups, cooked"),

        // Recipe 11 – Baked Lemon Salmon
        Ingredient(recipeId = 11, name = "Salmon fillets", amount = "4 x 180 g"),
        Ingredient(recipeId = 11, name = "Lemon", amount = "2"),
        Ingredient(recipeId = 11, name = "Garlic cloves", amount = "3, minced"),
        Ingredient(recipeId = 11, name = "Fresh dill", amount = "handful"),
        Ingredient(recipeId = 11, name = "Olive oil", amount = "2 tbsp"),
        Ingredient(recipeId = 11, name = "Asparagus", amount = "300 g"),

        // Recipe 12 – Chicken Tikka Masala
        Ingredient(recipeId = 12, name = "Chicken breast", amount = "600 g"),
        Ingredient(recipeId = 12, name = "Plain yogurt", amount = "1/2 cup"),
        Ingredient(recipeId = 12, name = "Heavy cream", amount = "1 cup"),
        Ingredient(recipeId = 12, name = "Tomato puree", amount = "400 g"),
        Ingredient(recipeId = 12, name = "Onion", amount = "1 large, diced"),
        Ingredient(recipeId = 12, name = "Garam masala", amount = "2 tsp"),
        Ingredient(recipeId = 12, name = "Ginger", amount = "1 tbsp, grated"),
        Ingredient(recipeId = 12, name = "Basmati rice & naan", amount = "to serve"),

        // Recipe 13 – Classic Crème Brûlée
        Ingredient(recipeId = 13, name = "Heavy cream", amount = "500 ml"),
        Ingredient(recipeId = 13, name = "Egg yolks", amount = "5"),
        Ingredient(recipeId = 13, name = "Sugar", amount = "100 g + extra for topping"),
        Ingredient(recipeId = 13, name = "Vanilla bean", amount = "1"),

        // Recipe 14 – Chocolate Lava Cake
        Ingredient(recipeId = 14, name = "Dark chocolate (70%)", amount = "170 g"),
        Ingredient(recipeId = 14, name = "Unsalted butter", amount = "115 g"),
        Ingredient(recipeId = 14, name = "Large eggs", amount = "2"),
        Ingredient(recipeId = 14, name = "Egg yolks", amount = "2"),
        Ingredient(recipeId = 14, name = "Powdered sugar", amount = "90 g"),
        Ingredient(recipeId = 14, name = "All-purpose flour", amount = "60 g"),
        Ingredient(recipeId = 14, name = "Vanilla ice cream", amount = "to serve"),

        // Recipe 15 – Mango Panna Cotta
        Ingredient(recipeId = 15, name = "Heavy cream", amount = "500 ml"),
        Ingredient(recipeId = 15, name = "Sugar", amount = "80 g"),
        Ingredient(recipeId = 15, name = "Gelatin sheets", amount = "4"),
        Ingredient(recipeId = 15, name = "Vanilla extract", amount = "1 tsp"),
        Ingredient(recipeId = 15, name = "Fresh mango", amount = "2, for topping"),
        Ingredient(recipeId = 15, name = "Lime juice", amount = "1 tbsp"),

        // Recipe 16 – Strawberry Cheesecake
        Ingredient(recipeId = 16, name = "Cream cheese", amount = "600 g"),
        Ingredient(recipeId = 16, name = "Digestive biscuits", amount = "250 g"),
        Ingredient(recipeId = 16, name = "Melted butter", amount = "100 g"),
        Ingredient(recipeId = 16, name = "Sugar", amount = "150 g"),
        Ingredient(recipeId = 16, name = "Large eggs", amount = "3"),
        Ingredient(recipeId = 16, name = "Sour cream", amount = "200 ml"),
        Ingredient(recipeId = 16, name = "Fresh strawberries", amount = "300 g"),
        Ingredient(recipeId = 16, name = "Strawberry jam (glaze)", amount = "3 tbsp")
    )
}
