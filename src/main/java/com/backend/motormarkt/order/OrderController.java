package com.backend.motormarkt.order;

import com.backend.motormarkt.order.Order;
import com.backend.motormarkt.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/orders")
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAll(){
        return this.orderService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) { return this.orderService.getById(id); }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Order order){
        return this.orderService.save(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Order order){
        return this.orderService.update(id, order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return this.orderService.delete(id);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<Object> getOrdersForUser(@PathVariable Long user_id) {
        return this.orderService.getOrdersForUser(user_id);
    }
}
