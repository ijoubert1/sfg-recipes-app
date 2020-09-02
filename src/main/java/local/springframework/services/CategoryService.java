package local.springframework.services;


import local.springframework.commands.CategoryCommand;

public interface CategoryService {

    public CategoryCommand createCategory(CategoryCommand categoryCommand);
}
