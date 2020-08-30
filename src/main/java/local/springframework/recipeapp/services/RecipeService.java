package local.springframework.recipeapp.services;

import local.springframework.recipeapp.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
