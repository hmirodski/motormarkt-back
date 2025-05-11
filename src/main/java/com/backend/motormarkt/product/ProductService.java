package com.backend.motormarkt.product;

import com.backend.motormarkt.category.Category;
import com.backend.motormarkt.category.CategoryRepository;
import com.backend.motormarkt.image_product.ImageProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAll(){
        return this.productRepository.findAll();
    }

    public ResponseEntity<Object> getById(Long id) {
        HashMap<String, Object> data = new HashMap<>();

        Optional<Product> product = this.productRepository.findById(id);
        if (product.isPresent()){
            data.put("data", product.get());
            return new ResponseEntity<>(
                    data,
                    HttpStatus.ACCEPTED
            );
        }else{
            data.put("error", true);
            data.put("message", "The product does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> save(Product product) {
        Optional<Product> response = this.productRepository.findProductByReference(product.getReference());
        HashMap<String, Object> data = new HashMap<>();

        if(response.isPresent()){
            data.put("error", true);
            data.put("message", "The product already exists");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        if(product.getCategory()!= null && product.getCategory().getId() != null){
            Optional<Category> categoryBD = this.categoryRepository.findById(product.getCategory().getId());
            if(categoryBD.isPresent()){
                product.setCategory(categoryBD.get());
            }else{
                data.put("error", true);
                data.put("message", "The category does not exist");

                return new ResponseEntity<>(
                        data,
                        HttpStatus.CONFLICT
                );
            }
        }

        this.productRepository.save(product);
        data.put("data", product);
        data.put("message", "Successfully saved");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> update(Long id, Product product) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<Product> productBD = this.productRepository.findById(id);

        if(productBD.isPresent()){
            if(product.getReference() != null && !product.getReference().equals(productBD.get().getReference())){
                Optional<Product> response = this.productRepository.findProductByReference(product.getReference());
                if (response.isPresent()){
                    data.put("error", true);
                    data.put("message", "A product with this reference already exists");

                    return new ResponseEntity<>(
                            data,
                            HttpStatus.CONFLICT
                    );
                }

                productBD.get().setReference(product.getReference());
            }

            if(product.getName() != null)
                productBD.get().setName(product.getName());

            if(product.getDescription() != null)
                productBD.get().setDescription(product.getDescription());

            if(product.getPrice() != null)
                productBD.get().setPrice(product.getPrice());

            if(product.getQuantity() != null)
                productBD.get().setQuantity(product.getQuantity());

            if(product.getCategory() != null && product.getCategory().getId() != null){
                Optional<Category> categoryBD = this.categoryRepository.findById(product.getCategory().getId());
                if(categoryBD.isPresent()){
                    productBD.get().setCategory(categoryBD.get());
                }else{
                    data.put("error", true);
                    data.put("message", "The category does not exist");

                    return new ResponseEntity<>(
                            data,
                            HttpStatus.CONFLICT
                    );
                }
            }


            this.productRepository.save(productBD.get());
            data.put("data", productBD.get());
            data.put("message", "Successfully saved");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.CREATED
            );
        }else{
            data.put("error", true);
            data.put("message", "The product does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> delete(Long id){
        HashMap<String, Object> data = new HashMap<>();

        if(!this.productRepository.existsById(id)){
            data.put("error", true);
            data.put("message", "The product does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        this.productRepository.deleteById(id);
        data.put("message", "Product deleted");

        return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
        );
    }

    public ResponseEntity<Object> getByCategoryId(Long category_id) {
        HashMap<String, Object> data = new HashMap<>();

        Optional<Category> categoryOptional = this.categoryRepository.findById(category_id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            List<Product> products = this.productRepository.findByCategory(category);
            data.put("data", products);

            return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
            );
        }else{
            data.put("error", true);
            data.put("message", "The category does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

    }


}
