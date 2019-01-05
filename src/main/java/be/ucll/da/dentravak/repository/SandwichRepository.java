
package be.ucll.da.dentravak.repository;

import be.ucll.da.dentravak.domain.Sandwich;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SandwichRepository extends CrudRepository<Sandwich, UUID> {

    Optional<Sandwich> findById(UUID id);
    List<Sandwich> findAll();

}
