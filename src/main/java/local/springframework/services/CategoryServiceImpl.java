package local.springframework.services;

import local.springframework.commands.CategoryCommand;
import local.springframework.converters.CategoryCommandToCategory;
import local.springframework.converters.CategoryToCategoryCommand;
import local.springframework.model.Category;
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
    final CategoryRepository repository;
    final RecipeRepository recipeRepository;

    public CategoryServiceImpl(CategoryCommandToCategory categoryCommandToCategory, CategoryToCategoryCommand categoryToCategoryCommand, CategoryRepository repository, RecipeRepository recipeRepository) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.repository = repository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public CategoryCommand createCategory(CategoryCommand categoryCommand) {
        log.info("Creating new category");
        Category convertedCategory = categoryCommandToCategory.convert(categoryCommand);
        Category savedCategory = repository.save(convertedCategory);
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

        return repository.findById(Long.parseLong(id)).orElse(null);
    }

    @Override
    public Set<Category> findAll(){
        Set<Category> categories = new HashSet<>();
        repository.findAll().iterator().forEachRemaining(categories::add);
        return categories;
    }

    @Override
    public CategoryCommand updateCategory(CategoryCommand categoryCommand){
        if(categoryCommand == null || categoryCommand.getId() == null || categoryCommand.getId().isEmpty()){
            throw new RuntimeException("Invalid input category");
        }

        Optional<Category> existingCategory = repository.findById(Long.parseLong(categoryCommand.getId()));
        if(!existingCategory.isPresent()){
            throw new RuntimeException("Invalid Category");
        }

        CategoryCommandToCategory categoryCommandToCategory = new CategoryCommandToCategory();
        Category cat = categoryCommandToCategory.convert(categoryCommand);

        Category saved = repository.save(cat);
        if(saved == null){
            throw new RuntimeException("Category not saved");
        }

        CategoryToCategoryCommand categoryToCategoryCommand = new CategoryToCategoryCommand();
        CategoryCommand command1 = categoryToCategoryCommand.convert(saved);

        return command1;
    }

    @Override
    public Set<Category> deleteCategoryById(String id){
        Set<Category> remainingCategories = new HashSet<>();



        repository.deleteById(Long.parseLong(id));
        repository.findAll().iterator().forEachRemaining(remainingCategories::add);
        return remainingCategories;
    }
}
