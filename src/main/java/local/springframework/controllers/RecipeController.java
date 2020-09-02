package local.springframework.controllers;

import local.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/recipes", "/recipesList"})
    public String listRecipes(Model model){
        log.info("Getting listRecipes");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "recipes/index";
    }

    @RequestMapping({"/recipes/{id}/show/"})
    public String getRecipe(@PathVariable Long id, Model model){
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        return "recipes/show";
    }
}
