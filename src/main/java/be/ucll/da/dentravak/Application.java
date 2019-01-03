package be.ucll.da.dentravak;

import be.ucll.da.dentravak.domain.Sandwich;
import be.ucll.da.dentravak.repository.SandwichRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

//@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(SandwichRepository repository) {

        return (String... args) -> {
            // save a couple of sandwiches
            repository.save(new Sandwich("Gezond", "Groentjes", new BigDecimal("4.00")));
            repository.save(new Sandwich("Martino", "Preparé en juunkes", new BigDecimal("4.00")));
            repository.save(new Sandwich("Boulet", "Boulet en juunkes", new BigDecimal("4.50")));

        };
    }
}