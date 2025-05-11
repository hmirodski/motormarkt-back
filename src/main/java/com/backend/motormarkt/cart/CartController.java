package com.backend.motormarkt.cart;

import com.backend.motormarkt.cart.Cart;
import com.backend.motormarkt.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/carts")
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping
    public List<Cart> getAll(){
        return this.cartService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) { return this.cartService.getById(id); }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Cart order){
        return this.cartService.save(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Cart order){
        return this.cartService.update(id, order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return this.cartService.delete(id);
    }
}
