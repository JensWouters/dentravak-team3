package be.ucll.da.dentravak.controller;

import be.ucll.da.dentravak.domain.Sandwich;
import be.ucll.da.dentravak.model.SandwichPreferences;
import be.ucll.da.dentravak.repository.SandwichRepository;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.naming.ServiceUnavailableException;
import java.net.URI;
import java.util.*;

@RestController
public class SandwichController {

    @Inject
    private DiscoveryClient discoveryClient;

    @Inject
    private SandwichRepository repo;

    @Inject
    private RestTemplate restTemplate;

    public SandwichController(SandwichRepository repo ) {
        this.repo = repo;
    }

    @RequestMapping("/sandwiches")
    public List<Sandwich> sandwiches() {

        try {
            SandwichPreferences preferences = getPreferences("ronald.dehuysser@ucll.be");
            List<Sandwich> sandwiches = sortByPreferences(preferences);
            System.out.println(sandwiches);
            return sandwiches;
        } catch (Exception e) {
            System.out.println("eogjaeragjerg");
            return repo.findAll();
        }
    }


    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.GET)
    public Sandwich getSandwichById(@PathVariable(value="id") UUID id) {
        return repo.findById(id).get();
    }

    @RequestMapping(value = "/sandwiches", method = RequestMethod.POST)
    public Sandwich addSandwich(@RequestBody Sandwich s) {
       Sandwich sandwich =  new Sandwich(s.getName(), s.getIngredients(), s.getPrice());

       return repo.save(sandwich);

    }

    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.PUT)
    public Sandwich updateSandwich(@PathVariable(value="id") UUID id, @RequestBody Sandwich newSandwich) {
        Optional<Sandwich> s = repo.findById(id);
            s.get().setName(newSandwich.getName());
            s.get().setIngredients(newSandwich.getIngredients());
            s.get().setPrice(newSandwich.getPrice());
        return repo.save(s.get());

    }

    // why comment: for testing
    @GetMapping("/getpreferences/{emailAddress}")
    public SandwichPreferences getPreferences(@PathVariable String emailAddress) throws RestClientException, ServiceUnavailableException {
        URI service = recommendationServiceUrl()
                .map(s -> s.resolve("/recommendation/recommend/" + emailAddress))
                .orElseThrow(ServiceUnavailableException::new);
        return restTemplate
                .getForEntity(service, SandwichPreferences.class)
                .getBody();
    }



    public Optional<URI> recommendationServiceUrl() {
        return discoveryClient.getInstances("recommendation")
                .stream()
                .map(si -> si.getUri())
                .findFirst();
    }
    public List<Sandwich> sortByPreferences(SandwichPreferences preferences) {
        List<Sandwich> allSandwiches = repo.findAll();
        System.out.println(preferences);
        System.out.println(allSandwiches.size());
        Collections.sort(allSandwiches, Comparator.comparing((Sandwich sandwich) -> rating(preferences, sandwich)).reversed());
        return allSandwiches;
    }

    private Float rating(SandwichPreferences preferences, Sandwich sandwich) {
        if (preferences.getRatingForSandwich(sandwich.getId()) == null) {
            return Float.parseFloat("0");
        }
        return preferences.getRatingForSandwich(sandwich.getId());
    }
}
