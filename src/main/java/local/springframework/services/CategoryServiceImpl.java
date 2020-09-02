package local.springframework.services;

import local.springframework.commands.CategoryCommand;
import local.springframework.converters.CategoryCommandToCategory;
import local.springframework.converters.CategoryToCategoryCommand;
import local.springframework.model.Category;
import local.springframework.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    final CategoryCommandToCategory categoryCommandToCategory;
    final CategoryToCategoryCommand categoryToCategoryCommand;
    final CategoryRepository repository;

    public CategoryServiceImpl(CategoryCommandToCategory categoryCommandToCategory, CategoryToCategoryCommand categoryToCategoryCommand, CategoryRepository repository) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.repository = repository;
    }

    @Override
    public CategoryCommand createCategory(CategoryCommand categoryCommand) {
        log.info("Creating new category: " + categoryCommand);
        Category convertedCategory = categoryCommandToCategory.convert(categoryCommand);
        Category savedCategory = repository.save(convertedCategory);
        if(savedCategory == null || savedCategory.getId() == null){
            throw new RuntimeException("Error creating category");
        }
        log.info("New category id: " + savedCategory.getId());
        return categoryToCategoryCommand.convert(savedCategory);
    }
}
