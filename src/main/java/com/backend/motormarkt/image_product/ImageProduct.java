package com.backend.motormarkt.image_product;

import com.backend.motormarkt.product.Product;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.DependsOn;

import java.time.LocalDateTime;

@Entity
@Table(name = "images_product")
@DependsOn("products")
public class ImageProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @Column(columnDefinition = "VARCHAR(100)")
    private String name;

    @CreationTimestamp
    private LocalDateTime created_at; // Se establecerá automáticamente al crear

    @UpdateTimestamp
    private LocalDateTime updated_at; // Se actualizará automáticamente al modificar

    public ImageProduct() {
    }

    public ImageProduct(Product product, String name, LocalDateTime created_at, LocalDateTime updated_at) {
        this.product = product;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public ImageProduct(Long id, Product product, String name, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "ImageProduct [id=" + id + ", product=" + product + ", name=" + name + ", created_at=" + created_at
                + ", updated_at=" + updated_at + "]";
    }

    
}
