package com.backend.motormarkt.product;

import com.backend.motormarkt.category.Category;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.DependsOn;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@DependsOn("categories")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(25)")
    private String reference;
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private Double price;
    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer quantity;
    @CreationTimestamp
    private LocalDateTime created_at; // Se establecerá automáticamente al crear
    @UpdateTimestamp
    private LocalDateTime updated_at; // Se actualizará automáticamente al modificar

    @ManyToOne
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Category category;

    public Product() {
    }

    public Product(Long id, String reference, String name, String description, Double price, Integer quantity, LocalDateTime created_at, LocalDateTime updated_at, Category category) {
        this.id = id;
        this.reference = reference;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.category = category;
    }

    public Product(String reference, String name, String description, Double price, Integer quantity, LocalDateTime created_at, LocalDateTime updated_at, Category category) {
        this.reference = reference;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", category=" + category +
                '}';
    }
}
