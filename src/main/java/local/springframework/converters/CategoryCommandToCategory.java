package local.springframework.converters;

import local.springframework.commands.CategoryCommand;
import local.springframework.model.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {
    @Override
    public Category convert(CategoryCommand categoryCommand) {
        if(categoryCommand == null){
            return null;
        }
        final Category category = new Category();
        if(categoryCommand.getId() != null && !categoryCommand.getId().isEmpty()){
            category.setId(Long.parseLong(categoryCommand.getId()));
        }

        category.setDescription(categoryCommand.getDescription());
        return category;
    }
}
