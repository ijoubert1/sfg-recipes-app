package local.springframework.services;

import local.springframework.commands.CategoryCommand;
import local.springframework.converters.CategoryCommandToCategory;
import local.springframework.converters.CategoryToCategoryCommand;
import local.springframework.model.Category;
import local.springframework.model.Recipe;
import local.springframework.repositories.CategoryRepository;
import local.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    final CategoryCommandToCategory categoryCommandToCategory;
    final CategoryToCategoryCommand categoryToCategoryCommand;
    final CategoryRepository categoryRepository;
    final RecipeRepository recipeRepository;

    public CategoryServiceImpl(CategoryCommandToCategory categoryCommandToCategory, CategoryToCategoryCommand categoryToCategoryCommand, CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public CategoryCommand createCategory(CategoryCommand categoryCommand) {
        log.info("Creating new category");
        Category convertedCategory = categoryCommandToCategory.convert(categoryCommand);
        Category savedCategory = categoryRepository.save(convertedCategory);
        if(savedCategory == null || savedCategory.getId() == null){
            throw new RuntimeException("Error creating category");
        }
        log.info("New category id: " + savedCategory.getId());
        return categoryToCategoryCommand.convert(savedCategory);
    }

    @Override
    public Category getCategoryById(String id) {
        if(id ==null || id.isEmpty()){
            return null;
        }

        return categoryRepository.findById(Long.parseLong(id)).orElse(null);
    }

    @Override
    public Set<Category> findAll(){
        Set<Category> categories = new HashSet<>();
        categoryRepository.findAll().iterator().forEachRemaining(categories::add);
        return categories;
    }

    @Override
    public CategoryCommand updateCategory(CategoryCommand categoryCommand){
        if(categoryCommand == null || categoryCommand.getId() == null || categoryCommand.getId().isEmpty()){
            throw new RuntimeException("Invalid input category");
        }

        Optional<Category> existingCategory = categoryRepository.findById(Long.parseLong(categoryCommand.getId()));
        if(!existingCategory.isPresent()){
            throw new RuntimeException("Invalid Category");
        }

        CategoryCommandToCategory categoryCommandToCategory = new CategoryCommandToCategory();
        Category cat = categoryCommandToCategory.convert(categoryCommand);

        Category saved = categoryRepository.save(cat);
        if(saved == null){
            throw new RuntimeException("Category not saved");
        }

        CategoryToCategoryCommand categoryToCategoryCommand = new CategoryToCategoryCommand();
        CategoryCommand command1 = categoryToCategoryCommand.convert(saved);

        return command1;
    }

    @Override
    public Set<Category> deleteCategoryById(String id){
        Optional<Category> foundCatOpt = categoryRepository.findById(Long.parseLong(id));
        Set<Recipe> recipesByCat = new HashSet<>();
        Set<Category> remainingCat = new HashSet<>();
        Set<Recipe> updatedRecipes = new HashSet<>();
        if(foundCatOpt.isPresent()){
            Category foundCat = foundCatOpt.get();
            recipesByCat = foundCat.getRecipes();
            for(Recipe r : recipesByCat){
                Optional<Recipe> foundROpt = recipeRepository.findById(r.getId());
                if(foundROpt.isPresent()){
                    Recipe foundR = foundROpt.get();

                    //Removing category from Recipe
                    boolean isRemoved = foundR.getCategories().remove(foundCat);
                    if(isRemoved){
                        //Updating Recipe
                        Recipe updatedRecipe = recipeRepository.save(foundR);
                        updatedRecipes.add(updatedRecipe);
                    } else {
                        throw new RuntimeException("Category: " + foundCat.getId() + " not removed from recipe: " + foundR.getId());
                    }
                } else {
                    log.info("Recipe not found for Category: " + foundCat.getId());
                }
            }

            //Removing all recipes from Category
            //foundCat.getRecipes().removeAll(recipesByCat);
            //categoryRepository.save(foundCat);

            //Removing Category
            categoryRepository.deleteById(foundCat.getId());
        } else {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.findAll().iterator().forEachRemaining(remainingCat::add);
        return remainingCat;
    }
}
