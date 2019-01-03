package be.ucll.da.dentravak.controller;

import be.ucll.da.dentravak.domain.Order;
import be.ucll.da.dentravak.repository.OrderRepository;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.time.LocalDateTime;

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
        //Order order = new Order(o.getSandwichId(), o.getName(), o.getBreadType(), o.getPrice(), o.getMobilePhoneNumber());

        o.setCreationDate(LocalDateTime.now());
        return repo.save(o);
    }

    @RequestMapping(value="/orders", method = RequestMethod.GET)
    public Iterable<Order> getOrders(@RequestParam(value = "date", required = false) String creationDate) {

//        if (creationDate == null) {
//            return (List) repo.findAll();
//        } else {
//            List<Order> orders = (List) repo.findAll();
//            List<Order> ordersOnDate = new ArrayList();
//            for (Order o : orders) {
//                if (o.getCreationDate().split("T")[0].equals(creationDate)) {
//                    ordersOnDate.add(o);
//                }
//            }
//            return ordersOnDate;
//        }
        return repo.findAll();
    }

}
