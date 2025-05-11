package com.backend.motormarkt.image_product;

import com.backend.motormarkt.image_product.ImageProduct;
import com.backend.motormarkt.product.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProduct, Long> {
    List<ImageProduct> findByProduct(Product product);
}
