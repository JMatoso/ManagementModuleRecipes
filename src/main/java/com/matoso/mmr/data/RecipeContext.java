package com.matoso.mmr.data;

import com.matoso.mmr.entities.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeContext {
    private final static ArrayList<Recipe> _recipes = new ArrayList<>();

    public static List<Recipe> getAll() {
        return _recipes;
    }

    public static int count() {
        return _recipes.size();
    }

    public static void add(Recipe @NotNull ... recipes) {
        Collections.addAll(_recipes, recipes);
    }

    public static @Nullable Recipe get(int id) {
        return getAll().stream()
                .filter(recipe -> recipe.getId() == id || recipe.getUserId() == id)
                .findFirst().orElse(null);

    }

    public static @NotNull List<Recipe> get(String key) {
        return getAll().stream()
                .filter(recipe -> String.valueOf(recipe.getSalary()).equals(key) ||
                    String.valueOf(recipe.getInvestment()).equals(key) ||
                    String.valueOf(recipe.getCreated()).contains(key))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static void update(@NotNull Recipe recipe) {
        if (remove(recipe.getId())) {
            _recipes.add(recipe);
        }
    }

    public static boolean remove(int id) {
        var recipe = get(id);
        return recipe != null && _recipes.remove(recipe);
    }
}

