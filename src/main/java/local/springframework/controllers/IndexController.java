package local.springframework.controllers;

import local.springframework.model.Recipe;
import local.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Slf4j
@Controller
public class IndexController {
    private final RecipeService recipeService;

    /*
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }*/

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String getIndexPage(Model model){
        /*
        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("tablespoon");
        log.info("Category id is: " + (categoryOptional.isPresent()  ? categoryOptional.get().getId() : "Cat not found"));
        log.info("Unit of Measure id is: " + (unitOfMeasureOptional.isPresent() ? unitOfMeasureOptional.get().getId() : "Unit not found"));
        */

        Set<Recipe> recipeSet = recipeService.getRecipes();
        log.info("Loaded recipes: " + recipeSet.size());

        model.addAttribute("recipes", recipeSet);
        return "index";
    }


}
