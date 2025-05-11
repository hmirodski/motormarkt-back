package com.backend.motormarkt.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/products")
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll(){
        return this.productService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) { return this.productService.getById(id); }

    @GetMapping("/category/{category_id}")
    public ResponseEntity<Object> getByCategoryId(@PathVariable Long category_id){
        return this.productService.getByCategoryId(category_id);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Product product){
        return this.productService.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Product product){
        return this.productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return this.productService.delete(id);
    }

}
