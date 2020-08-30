package local.springframework.recipeapp.controllers;

import local.springframework.recipeapp.model.Category;
import local.springframework.recipeapp.model.UnitOfMeasure;
import local.springframework.recipeapp.repositories.CategoryRepository;
import local.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String getIndexPage(){
        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

        System.out.println("Category id is: " + (categoryOptional.isPresent() ? categoryOptional.get().getId() : "Cat not found"));
        System.out.println("Unit of Measure id is: " + (unitOfMeasureOptional.isPresent() ? unitOfMeasureOptional.get().getId() : "Unit not found"));
        return "index";
    }


}
