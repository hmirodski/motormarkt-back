package com.backend.motormarkt.order_line;

import com.backend.motormarkt.order_line.OrderLine;
import com.backend.motormarkt.order_line.OrderLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/orders_lines")
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class OrderLineController {
    private final OrderLineService orderLineService;

    @Autowired
    public OrderLineController(OrderLineService orderLineService){
        this.orderLineService = orderLineService;
    }

    @GetMapping
    public List<OrderLine> getAll(){
        return this.orderLineService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) { return this.orderLineService.getById(id); }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody OrderLine orderLine){
        return this.orderLineService.save(orderLine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody OrderLine orderLine){
        return this.orderLineService.update(id, orderLine);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return this.orderLineService.delete(id);
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<Object> getOrderLinesForOrder(@PathVariable Long order_id) {
        return this.orderLineService.getOrderLinesForOrder(order_id);
    }
}
