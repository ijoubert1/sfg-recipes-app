package local.springframework.recipeapp.controllers;

import local.springframework.recipeapp.model.Category;
import local.springframework.recipeapp.model.Recipe;
import local.springframework.recipeapp.model.UnitOfMeasure;
import local.springframework.recipeapp.repositories.CategoryRepository;
import local.springframework.recipeapp.repositories.RecipeRepository;
import local.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Controller
public class IndexController {
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String getIndexPage(Model model){
        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("tablespoon");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);

        log.info("Category id is: " + (categoryOptional.isPresent()  ? categoryOptional.get().getId() : "Cat not found"));
        log.info("Unit of Measure id is: " + (unitOfMeasureOptional.isPresent() ? unitOfMeasureOptional.get().getId() : "Unit not found"));
        log.info("Loaded recipes: " + recipeSet.size());

        model.addAttribute("recipes", recipeRepository.findAll());
        return "index";
    }


}
