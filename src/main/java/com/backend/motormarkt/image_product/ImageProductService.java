package com.backend.motormarkt.image_product;

import com.backend.motormarkt.image_product.ImageProduct;
import com.backend.motormarkt.image_product.ImageProductRepository;
import com.backend.motormarkt.product.Product;
import com.backend.motormarkt.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageProductService {
    private final ImageProductRepository imageProductRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ImageProductService(ImageProductRepository imageProductRepository, ProductRepository productRepository){
        this.imageProductRepository = imageProductRepository;
        this.productRepository = productRepository;
    }

    public List<ImageProduct> getAll(){
        return this.imageProductRepository.findAll();
    }

    public ResponseEntity<Object> getById(Long id) {
        HashMap<String, Object> data = new HashMap<>();

        Optional<ImageProduct> imageProduct = this.imageProductRepository.findById(id);
        if (imageProduct.isPresent()){
            data.put("data", imageProduct.get());
            return new ResponseEntity<>(
                    data,
                    HttpStatus.ACCEPTED
            );
        }else{
            data.put("error", true);
            data.put("message", "The imageProduct does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> save(Long product_id, MultipartFile image) throws IOException{
        HashMap<String, Object> data = new HashMap<>();

        String filename = UUID.randomUUID().toString();
        byte[] bytes = image.getBytes();
        String fileOriginalName = image.getOriginalFilename();

        long fileSize = image.getSize();
        long maxFileSize = 1 * 1024 * 1024;

        if(fileSize > maxFileSize){
            data.put("error", true);
            data.put("message", "Image size is too large");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        if(
            !fileOriginalName.endsWith(".jpg") &&
            !fileOriginalName.endsWith(".jpeg") &&
            !fileOriginalName.endsWith(".png")
        ){
            data.put("error", true);
            data.put("message", "Only JPG, JPEG, PNG files are allowed!");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
        String newFileName = filename + fileExtension;

        File folder = new File("src/main/resources/static");
        if(!folder.exists()){
            folder.mkdirs();
        }

        Path path = Paths.get("src/main/resources/static/", newFileName);
        Files.write(path, bytes);
        
        ImageProduct imageProduct = new ImageProduct();
        
        imageProduct.setProduct(productRepository.findById(product_id).get());
        imageProduct.setName(newFileName);
        this.imageProductRepository.save(imageProduct);

        data.put("data", imageProduct);
        data.put("message", "Successfully saved");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> delete(Long id) throws IOException{
        HashMap<String, Object> data = new HashMap<>();

        if(!this.imageProductRepository.existsById(id)){
            data.put("error", true);
            data.put("message", "The imageProduct does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        Optional<ImageProduct> imageBD = this.imageProductRepository.findById(id);
        Path imagePath = Paths.get("src/main/resources/static").resolve(imageBD.get().getName()).normalize();
        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
        }

        this.imageProductRepository.deleteById(id);
        data.put("message", "imageProduct deleted");

        return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
        );
    }

    public ResponseEntity<Object> getImagesForProduct(Long product_id) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<Product> productOptional = this.productRepository.findById(product_id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            List<ImageProduct> images = imageProductRepository.findByProduct(product);
            data.put("data", images);

            return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
            );
        } else {
            data.put("error", true);
            data.put("message", "The product does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

    }
}
