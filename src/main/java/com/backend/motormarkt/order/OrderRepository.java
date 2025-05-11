package com.backend.motormarkt.order;

import com.backend.motormarkt.image_product.ImageProduct;
import com.backend.motormarkt.order.Order;
import com.backend.motormarkt.product.Product;
import com.backend.motormarkt.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderByReference(String reference);
    List<Order> findByUser(User user);

}
