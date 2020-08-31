package local.springframework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
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

}
