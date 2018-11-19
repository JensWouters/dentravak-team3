
package be.ucll.da.dentravak.repository;

import be.ucll.da.dentravak.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, String> {

    List<Optional<Order>> findByCreationDate(String creationDate);

}
