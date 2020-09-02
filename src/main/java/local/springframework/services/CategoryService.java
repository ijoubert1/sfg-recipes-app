package local.springframework.services;


import local.springframework.commands.CategoryCommand;
import local.springframework.model.Category;

public interface CategoryService {

    public CategoryCommand createCategory(CategoryCommand categoryCommand);

    public Category getCategoryById(String id);
}
