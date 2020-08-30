package local.springframework.recipeapp.bootstrap;

import local.springframework.recipeapp.model.*;
import local.springframework.recipeapp.repositories.CategoryRepository;
import local.springframework.recipeapp.repositories.RecipeRepository;
import local.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RecipeBootStrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootStrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }


    public List<Recipe> getRecipes(){
        List<Recipe> recipes = new ArrayList<>();

        Optional<Category> americanOptional = categoryRepository.findByDescription("American");
        if(!americanOptional.isPresent()){
            throw new RuntimeException("American category not found");
        }
        Category american = americanOptional.get();

        Optional<Category> mexicanOptional = categoryRepository.findByDescription("Mexican");
        if(!mexicanOptional.isPresent()){
            throw new RuntimeException("Mexican category not found");
        }
        Category mexican = mexicanOptional.get();


        Optional<UnitOfMeasure> unitOptional = unitOfMeasureRepository.findByDescription("unit");
        if(!unitOptional.isPresent()){
            throw new RuntimeException("UOM unit not found");
        }
        UnitOfMeasure unit = unitOptional.get();

        Optional<UnitOfMeasure> teaspoonOptional = unitOfMeasureRepository.findByDescription("teaspoon");
        if(!teaspoonOptional.isPresent()){
            throw new RuntimeException("UOM teaspoon not found");
        }
        UnitOfMeasure teaspoon = teaspoonOptional.get();

        Optional<UnitOfMeasure> tablespoonOptional = unitOfMeasureRepository.findByDescription("tablespoon");
        if(!tablespoonOptional.isPresent()){
            throw new RuntimeException("UOM tablespoon not found");
        }
        UnitOfMeasure tablespoon = tablespoonOptional.get();

        Optional<UnitOfMeasure> cupOptional = unitOfMeasureRepository.findByDescription("cup");
        if(!cupOptional.isPresent()){
            throw new RuntimeException("UOM cup not found");
        }
        UnitOfMeasure cup = cupOptional.get();

        Recipe guacamole = new Recipe();
        guacamole.getCategories().add(mexican);
        guacamole.getCategories().add(american);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setDescription("The best guacamole keeps it simple: just ripe avocados, salt, a squeeze of lime, onions, chiles, cilantro, and some chopped tomato. Serve it as a dip at your next party or spoon it on top of tacos for an easy dinner upgrade.");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(15);
        guacamole.setSource("Unkown");
        guacamole.setServings(4);
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        Note guacamoleNote = new Note();
        guacamoleNote.setDescription("Be careful handling chiles if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours.");
        guacamoleNote.setRecipe(guacamole);
        guacamole.setNotes(guacamoleNote);

        Set<Ingredient> guacamoleIngredients = new HashSet<>();
        Ingredient i1 = new Ingredient();
        //i1.setRecipe(guacamole);
        i1.setAmount(2d);
        i1.setOum(unit);
        i1.setDescription("ripe avocados");
        guacamoleIngredients.add(i1);

        Ingredient i2 = new Ingredient();
        //i2.setRecipe(guacamole);
        i2.setAmount(0.25);
        i2.setOum(teaspoon);
        i2.setDescription("salt, more to taste");
        guacamoleIngredients.add(i2);

        Ingredient i3 = new Ingredient();
        //i3.setRecipe(guacamole);
        i3.setAmount(1d);
        i3.setOum(tablespoon);
        i3.setDescription("fresh lime juice or lemon juice");
        guacamoleIngredients.add(i3);

        Ingredient i4 = new Ingredient();
        //i4.setRecipe(guacamole);
        i4.setAmount(0.25);
        i4.setOum(cup);
        i4.setDescription("minced red onion or thinly sliced green onion");
        guacamoleIngredients.add(i4);

        Ingredient i5 = new Ingredient();
        i5.setAmount(2d);
        i5.setOum(unit);
        i5.setDescription("serrano chiles, stems and seeds removed, minced");
        guacamoleIngredients.add(i5);

        guacamole.setIngredients(guacamoleIngredients);
        guacamole.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl." +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving." +
                "\n" +
                "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.");

        recipeRepository.save(guacamole);
        recipes = (List<Recipe>) recipeRepository.findAll();
        return recipes;
    }
}
