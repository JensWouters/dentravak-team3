package controller;

import domain.Sandwich;
import domain.Sandwich.SandwichBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class SandwichController {

    /*private SandwichRepository repo;

    public SandwichController(SandwichRepository repo ) {
        this.repo = repo;
    }*/

    @RequestMapping("/sandwiches")
    public Sandwich sandwich() {
        return SandwichBuilder.aSandwich()
                .withIngredients("Kaas & Hesp")
                .withName("Smoske")
                .withPrice(new BigDecimal("3,50"))
                .build();
    }
}