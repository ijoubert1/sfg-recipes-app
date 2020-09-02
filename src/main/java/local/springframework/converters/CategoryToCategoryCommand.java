package local.springframework.converters;

import local.springframework.commands.CategoryCommand;
import local.springframework.model.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand>{

    @Override
    public CategoryCommand convert(Category category) {
        if(category == null){
            return null;
        }

        final CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(Long.toString(category.getId()));
        categoryCommand.setDescription(category.getDescription());
        return categoryCommand;
    }
}
