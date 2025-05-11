package com.backend.motormarkt.image_product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.backend.motormarkt.image_product.ImageProduct;
import com.backend.motormarkt.image_product.ImageProductService;
import com.backend.motormarkt.order.Order;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/images_product")
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class ImageProductController {

    @Autowired
    private final ImageProductService imageProductService;

    @Autowired
    public ImageProductController(ImageProductService imageProductService){
        this.imageProductService = imageProductService;
    }

    @GetMapping
    public List<ImageProduct> getAll(){
        return this.imageProductService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) { return this.imageProductService.getById(id); }

    @PostMapping
    public ResponseEntity<Object> save(@RequestParam("product_id") Long product_id, @RequestParam("image") MultipartFile image) throws IOException{
        return this.imageProductService.save(product_id, image);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) throws IOException{
        return this.imageProductService.delete(id);
    }

    @GetMapping("/static/{name_image}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String name_image) throws IOException{
        
        // Cargar la imagen desde la carpeta static
        InputStream inputStream = getClass().getResourceAsStream("/static/" + name_image);
        if (inputStream != null) {
            InputStreamResource resource = new InputStreamResource(inputStream);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Ajusta según el tipo de imagen
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{product_id}/images")
    public ResponseEntity<Object> getImagesForProduct(@PathVariable Long product_id) {
        return this.imageProductService.getImagesForProduct(product_id);
    }
}
