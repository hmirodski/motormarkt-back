package com.backend.motormarkt.cart;

import com.backend.motormarkt.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.DependsOn;

import java.time.LocalDateTime;

@Entity
@Table(name = "carts")
@DependsOn("users")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Cart() {
    }

    public Cart(Double total_price, LocalDateTime created_at, LocalDateTime updated_at, User user) {
        this.total_price = total_price;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user = user;
    }

    public Cart(Long id, Double total_price, LocalDateTime created_at, LocalDateTime updated_at, User user) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", total_price=" + total_price +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", user=" + user +
                '}';
    }
}
