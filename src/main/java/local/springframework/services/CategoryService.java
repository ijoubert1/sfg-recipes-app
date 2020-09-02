package local.springframework.services;


import local.springframework.commands.CategoryCommand;
import local.springframework.model.Category;

import java.util.Set;

public interface CategoryService {

    public CategoryCommand createCategory(CategoryCommand categoryCommand);

    public Category getCategoryById(String id);

    public Set<Category> findAll();

    public CategoryCommand updateCategory(CategoryCommand categoryCommand);
}
