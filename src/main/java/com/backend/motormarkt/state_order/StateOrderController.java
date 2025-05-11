package com.backend.motormarkt.state_order;

import com.backend.motormarkt.state_order.StateOrder;
import com.backend.motormarkt.state_order.StateOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/states_orders")
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class StateOrderController {
    private final StateOrderService stateOrderService;

    @Autowired
    public StateOrderController(StateOrderService stateOrderService){
        this.stateOrderService = stateOrderService;
    }

    @GetMapping
    public List<StateOrder> getAll(){
        return this.stateOrderService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) { return this.stateOrderService.getById(id); }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody StateOrder stateOrder){
        return this.stateOrderService.save(stateOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return this.stateOrderService.delete(id);
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<Object> getStatesOrderForOrder(@PathVariable Long order_id) {
        return this.stateOrderService.getStatesOrderForOrder(order_id);
    }
}
