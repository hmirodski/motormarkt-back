package com.backend.motormarkt.cart_line;

import com.backend.motormarkt.cart_line.CartLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartLineRepository extends JpaRepository<CartLine, Long> {
}
