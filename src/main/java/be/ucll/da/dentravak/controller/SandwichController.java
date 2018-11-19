package be.ucll.da.dentravak.controller;

import be.ucll.da.dentravak.domain.Sandwich;
import be.ucll.da.dentravak.repository.SandwichRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class SandwichController {

    private SandwichRepository repo;

    public SandwichController(SandwichRepository repo ) {
        this.repo = repo;
    }

    @RequestMapping("/sandwiches")
    public List<Sandwich> sandwich() {
        /*return SandwichBuilder.aSandwich()
                .withIngredients("Kaas & Hesp")
                .withName("Smoske")
                .withPrice(new BigDecimal("3.50"))
                .build();*/

        return (List) repo.findAll();
    }
    @RequestMapping(value = "/getSandwich/{id}", method = RequestMethod.GET)
    public Sandwich getSandwichById(@PathVariable(value="id") UUID id) {
        return repo.findById(id).get();
    }

    @RequestMapping(value = "/addSandwich", method = RequestMethod.POST)
    public void addSandwich(@RequestBody Sandwich s) {
       Sandwich sandwich =  new Sandwich(s.getName(), s.getIngredients(), s.getPrice());

       repo.save(sandwich);
    }

    @RequestMapping(value = "/updateSandwich/{id}", method = RequestMethod.PUT)
    public void updateSandwich(@PathVariable(value="id") UUID id, @RequestBody Sandwich newSandwich) {
        Optional<Sandwich> s = repo.findById(id);
            s.get().setName(newSandwich.getName());
            s.get().setIngredients(newSandwich.getIngredients());
            s.get().setPrice(newSandwich.getPrice());
        repo.save(s.get());
    }
}