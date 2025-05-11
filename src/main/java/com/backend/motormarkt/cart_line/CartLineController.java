package com.backend.motormarkt.cart_line;

import com.backend.motormarkt.cart_line.CartLine;
import com.backend.motormarkt.cart_line.CartLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/carts_lines")
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class CartLineController {
    private final CartLineService cartLineService;

    @Autowired
    public CartLineController(CartLineService cartLineService){
        this.cartLineService = cartLineService;
    }

    @GetMapping
    public List<CartLine> getAll(){
        return this.cartLineService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) { return this.cartLineService.getById(id); }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody CartLine cartLine){
        return this.cartLineService.save(cartLine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody CartLine cartLine){
        return this.cartLineService.update(id, cartLine);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return this.cartLineService.delete(id);
    }
}
