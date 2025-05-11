package com.backend.motormarkt.state_order;

import com.backend.motormarkt.order.Order;
import com.backend.motormarkt.state.State;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.DependsOn;

import java.time.LocalDateTime;

@Entity
@Table(name = "states_order")
@DependsOn({"states", "orders"})
public class StateOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "state_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private State state;

    @CreationTimestamp
    private LocalDateTime created_at; // Se establecerá automáticamente al crear

    @UpdateTimestamp
    private LocalDateTime updated_at; // Se actualizará automáticamente al modificar

    public StateOrder() {
    }

    public StateOrder(Order order, State state, LocalDateTime created_at, LocalDateTime updated_at) {
        this.order = order;
        this.state = state;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public StateOrder(Long id, Order order, State state, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.order = order;
        this.state = state;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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
        return "StateOrder{" +
                "id=" + id +
                ", order=" + order +
                ", state=" + state +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
