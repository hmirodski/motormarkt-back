package com.backend.motormarkt.order_line;

import com.backend.motormarkt.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.backend.motormarkt.order.Order;
import com.backend.motormarkt.product.Product;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.DependsOn;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_lines")
@DependsOn({"orders", "products"})
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double price_unit;

    @Column(nullable = false)
    private Double total_price;

    @Column(nullable = false)
    private Integer quantity;

    @CreationTimestamp
    private LocalDateTime created_at; // Se establecerá automáticamente al crear

    @UpdateTimestamp
    private LocalDateTime updated_at; // Se actualizará automáticamente al modificar

    @ManyToOne
    @JoinColumn(name = "order_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    public OrderLine() {
    }

    public OrderLine(Double price_unit, Double total_price, Integer quantity, LocalDateTime created_at, LocalDateTime updated_at, Order order, Product product) {
        this.price_unit = price_unit;
        this.total_price = total_price;
        this.quantity = quantity;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.order = order;
        this.product = product;
    }

    public OrderLine(Long id, Double price_unit, Double total_price, Integer quantity, LocalDateTime created_at, LocalDateTime updated_at, Order order, Product product) {
        this.id = id;
        this.price_unit = price_unit;
        this.total_price = total_price;
        this.quantity = quantity;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.order = order;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(Double price_unit) {
        this.price_unit = price_unit;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", price_unit=" + price_unit +
                ", total_price=" + total_price +
                ", quantity=" + quantity +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", order=" + order +
                ", product=" + product +
                '}';
    }
}
