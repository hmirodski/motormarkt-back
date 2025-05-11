package com.backend.motormarkt.category;

import com.backend.motormarkt.category.Category;
import com.backend.motormarkt.category.CategoryRepository;
import com.backend.motormarkt.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll(){
        return this.categoryRepository.findAll();
    }

    public ResponseEntity<Object> getById(Long id) {
        HashMap<String, Object> data = new HashMap<>();

        Optional<Category> category = this.categoryRepository.findById(id);
        if (category.isPresent()){
            data.put("data", category.get());
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

    public ResponseEntity<Object> save(Category category) {
        HashMap<String, Object> data = new HashMap<>();

        this.categoryRepository.save(category);
        data.put("data", category);
        data.put("message", "Successfully saved");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> delete(Long id){
        HashMap<String, Object> data = new HashMap<>();

        if(!this.categoryRepository.existsById(id)){
            data.put("error", true);
            data.put("message", "The category does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        this.categoryRepository.deleteById(id);
        data.put("message", "Category deleted");

        return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
        );
    }


    public ResponseEntity<Object> update(Long id, Category category) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<Category> categoryBD = this.categoryRepository.findById(id);

        if(categoryBD.isPresent()){
            if(category.getName() != null)
                categoryBD.get().setName(category.getName());

            if(category.getDescription() != null)
                categoryBD.get().setDescription(category.getDescription());

            this.categoryRepository.save(categoryBD.get());
            data.put("data", categoryBD.get());
            data.put("message", "Successfully saved");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.CREATED
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
