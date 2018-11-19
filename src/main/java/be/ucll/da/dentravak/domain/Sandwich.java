package be.ucll.da.dentravak.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Sandwich {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    private String name;
    private String ingredients;
    private BigDecimal price;

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private Sandwich() {};

    public Sandwich(String name, String ingredients, BigDecimal price) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    public static class SandwichBuilder {
        private String name;
        private String ingredients;
        private BigDecimal price;

        private SandwichBuilder() {}

        public static SandwichBuilder aSandwich() {return new SandwichBuilder();}


        public SandwichBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SandwichBuilder withIngredients(String ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public SandwichBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Sandwich build() {
            Sandwich sandwich = new Sandwich();
            sandwich.name = name;
            sandwich.ingredients = ingredients;
            sandwich.price = price;
            return sandwich;
        }
    }
}