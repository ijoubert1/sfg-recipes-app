package local.springframework.services;


import local.springframework.model.Category;
import local.springframework.model.Recipe;
import local.springframework.repositories.CategoryRepository;
import local.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CategoryServiceImplTest {
    CategoryServiceImpl categoryService;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    RecipeRepository recipeRepository;

    Category cat1;
    Category cat3;
    Recipe r1;
    Recipe r2;
    Set<Category> categories = new HashSet<>();



    @Before
    public void setUp() {
        cat1 = new Category();
        cat1.setId(1l);

        cat3 = new Category();
        cat3.setId(3l);

        r1 = new Recipe();
        r1.setId(1l);
        r1.getCategories().add(cat1);
        r1.getCategories().add(cat3);

        r2 = new Recipe();
        r2.setId(2l);
        r2.getCategories().add(cat1);
        r2.getCategories().add(cat3);

        cat1.getRecipes().add(r1);
        cat1.getRecipes().add(r2);

        cat3.getRecipes().add(r2);
        cat3.getRecipes().add(r2);

        categoryService = new CategoryServiceImpl(null, null,
                categoryRepository, recipeRepository);

        categories.add(cat3);
    }

    @Test
    public void deleteCategoryById() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(cat1));
        when(recipeRepository.findById(1l)).thenReturn(Optional.of(r1));
        when(recipeRepository.findById(2l)).thenReturn(Optional.of(r2));
        when(categoryRepository.findAll()).thenReturn(categories);
        categoryService.deleteCategoryById("1");
    }
}