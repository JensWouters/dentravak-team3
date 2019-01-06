package be.ucll.da.dentravak.controller;

import be.ucll.da.dentravak.domain.Order;
import be.ucll.da.dentravak.repository.OrderRepository;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class OrderController {
    @Inject
    private OrderRepository repo;
    @Inject
    private DiscoveryClient discoveryClient;

    public OrderController(OrderRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public Order addOrder(@RequestBody Order o) {
        o.setCreationDate(LocalDateTime.now());
        return repo.save(o);
    }

    @RequestMapping(value="/orders", method = RequestMethod.GET)
    public Iterable<Order> getOrders(@RequestParam(value = "date", required = false) String creationDate) {
        List<Order> orders;

        if (creationDate != null && !creationDate.trim().isEmpty()) {
            orders = new ArrayList<Order>();
            String[] dateParts = creationDate.split("-");

            for (Order order : repo.findAll()) {
                if (order.getCreationDate().getYear() == Integer.parseInt(dateParts[0])
                        && order.getCreationDate().getMonthValue() == Integer.parseInt(dateParts[1])
                        && order.getCreationDate().getDayOfMonth() == Integer.parseInt(dateParts[2])) {
                    orders.add(order);
                }
            }
        } else {
            orders = StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
        }

        return orders;
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.PUT)
    public Order putOrderById(@PathVariable(value="id") UUID id, @RequestBody Order order) {
        if(id.equals(order.getId())) {
            order.setPrinted(true);
            return repo.save(order);
        }else{
            throw new IllegalArgumentException("don't do that... stop!");
        }
    }

}
