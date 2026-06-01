# Integracja SeedData z bazą danych Room

## Podsumowanie zmian

Wszystkie dane z obiektu `SeedData` (kategorie, przepisy, składniki) zostały zintegrowane z bazą danych Room. Aplikacja teraz:

1. **Prepopuluje bazę** przy pierwszym uruchomieniu (callback `onCreate`)
2. **Czyta dane z Room** zamiast ze statycznej listy
3. **Umożliwia łatwe dodawanie** nowych przepisów przez proste wywołanie `dao.insertRecipe()`

---

## Zmienione pliki

### Nowy plik
| Plik | Opis |
|---|---|
| `data/local/RecipeDao.kt` | Nowy DAO z pełnym dostępem do tabel `Kategorie`, `Przepisy`, `Skladniki` |

### Zmodyfikowane pliki
| Plik | Co się zmieniło |
|---|---|
| `data/local/AppDatabase.kt` | Dodano `recipeDao()`, `PrepopulateCallback`, wersja → 4 |
| `data/repository/RecipeRepository.kt` | Wszystkie metody przełączone z `SeedData` na zapytania DAO |
| `viewmodel/HomeViewModel.kt` | `categories`, `allRecipes`, `searchResults` teraz jako `StateFlow` z bazy |
| `viewmodel/RecipeDetailsViewModel.kt` | `recipe` ładowany przez `suspend` DAO, nowy `ingredients` StateFlow |
| `ui/screens/HomeScreen.kt` | Zbiera `categories`/`allRecipes` jako StateFlow, `recipeOfDay` z `allRecipes` |
| `ui/screens/RecipeDetailsScreen.kt` | Składniki z ViewModel Flow zamiast `SeedData.INGREDIENTS` |
| `ui/screens/RecipeListScreen.kt` | Kategorie i przepisy przez Flow z repo zamiast `SeedData` |

### Pliki bez zmian
`SeedData.kt` (zachowany jako źródło prepopulacji), `FavoriteDao.kt`, `ShoppingItemDao.kt`, `NavGraph.kt`, `MainActivity.kt`

---

## Jak działa prepopulacja

```
Pierwsze uruchomienie → Room tworzy bazę → onCreate callback →
    SeedData.CATEGORIES  → INSERT INTO Kategorie
    SeedData.RECIPES     → INSERT INTO Przepisy
    SeedData.INGREDIENTS → INSERT INTO Skladniki
```

Ponieważ `fallbackToDestructiveMigration()` jest aktywne, bump wersji z 3→4 spowoduje usunięcie starej bazy i ponowne `onCreate`.

---

## Jak dodać nowe przepisy w przyszłości

```kotlin
// Gdziekolwiek masz dostęp do RecipeRepository lub RecipeDao:
val newRecipe = Recipe(
    id = 17,
    categoryId = 1,
    title = "Nowy Przepis",
    prepTime = "30 min",
    instructions = "Krok 1.\nKrok 2.\nKrok 3.",
    thumbnail = "https://example.com/image.jpg"
)
recipeDao.insertRecipe(newRecipe)
recipeDao.insertIngredients(listOf(
    Ingredient(recipeId = 17, name = "Mąka", amount = "2 cups"),
    Ingredient(recipeId = 17, name = "Cukier", amount = "1 cup")
))
```

Albo dodaj je do `SeedData.kt` — pojawią się po reinstalacji/czyszczeniu danych aplikacji.

---

## Weryfikacja w Android Studio

### Krok 1: Otwórz projekt
1. Otwórz Android Studio
2. **File → Open** → wskaż folder `SmartCookBook`
3. Poczekaj na zakończenie synchronizacji Gradle (pasek postępu na dole)

### Krok 2: Sprawdź czy się kompiluje
1. **Build → Make Project** (lub `Ctrl+F9`)
2. Panel **Build** na dole powinien pokazać `BUILD SUCCESSFUL`
3. Jeśli są błędy — sprawdź panel **Build Output** po szczegóły

### Krok 3: Uruchom na emulatorze/urządzeniu
1. Wybierz urządzenie z listy na górnym pasku (emulator lub fizyczne urządzenie USB)
2. Kliknij **Run ▶** (zielony trójkąt) lub `Shift+F10`
3. Aplikacja się zainstaluje i uruchomi

### Krok 4: Przetestuj funkcjonalności

| Co sprawdzić | Oczekiwany wynik |
|---|---|
| Ekran główny | Widoczne kategorie, przepis dnia, popularne przepisy |
| Kliknij kategorię | Lista przepisów w danej kategorii |
| Kliknij przepis | Szczegóły z listą składników |
| Zakładka Ingredients | Składniki z przyciskami dodania do listy zakupów |
| Zakładka Instructions | Kroki przygotowania |
| Wyszukiwarka | Wpisz np. "Chicken" — wyniki z bazy |
| Ulubione ❤️ | Dodawanie/usuwanie działa |
| Lista zakupów 🛒 | Składniki dodane z przepisu widoczne |

### Krok 5: Sprawdź bazę danych (opcjonalnie)
1. **View → Tool Windows → App Inspection**
2. Wybierz uruchomioną aplikację
3. Zakładka **Database Inspector**
4. Kliknij `smartcookbook_db` → sprawdź tabele `Kategorie`, `Przepisy`, `Skladniki`
5. Powinny zawierać odpowiednio 4, 16 i 100+ rekordów

> **Tip:** Jeśli baza jest pusta po uruchomieniu, odinstaluj aplikację i uruchom ponownie — wymusi to `onCreate` i prepopulację.

### Krok 6: Jeśli wystąpią błędy kompilacji
1. **File → Invalidate Caches → Invalidate and Restart**
2. Po restarcie: **Build → Clean Project**, potem **Build → Rebuild Project**
3. Upewnij się, że Gradle sync zakończył się pomyślnie (ikona słonia na pasku)
