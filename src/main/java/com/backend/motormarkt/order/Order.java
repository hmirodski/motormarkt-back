package com.backend.motormarkt.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.backend.motormarkt.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.DependsOn;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@DependsOn("users")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(25)")
    private String reference;

    @Column(columnDefinition = "VARCHAR(100)")
    private String payment;

    @Column(nullable = false)
    private Double total_price;

    @CreationTimestamp
    private LocalDateTime created_at; // Se establecerá automáticamente al crear

    @UpdateTimestamp
    private LocalDateTime updated_at; // Se actualizará automáticamente al modificar

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;

    public Order() {
    }

    public Order(String reference, String payment, Double total_price, LocalDateTime created_at, LocalDateTime updated_at, User user) {
        this.reference = reference;
        this.payment = payment;
        this.total_price = total_price;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user = user;
    }

    public Order(Long id, String reference, String payment, Double total_price, LocalDateTime created_at, LocalDateTime updated_at, User user) {
        this.id = id;
        this.reference = reference;
        this.payment = payment;
        this.total_price = total_price;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user = user;
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
