package local.springframework.recipeapp.model;

import javax.persistence.*;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double amount;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure oum;

    @ManyToOne
    private Recipe recipe;

    public Ingredient() {
        super();
    }

    public Ingredient(String description, Double amount, UnitOfMeasure oum) {
        this.description = description;
        this.amount = amount;
        this.oum = oum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public UnitOfMeasure getOum() {
        return oum;
    }

    public void setOum(UnitOfMeasure oum) {
        this.oum = oum;
    }
}
