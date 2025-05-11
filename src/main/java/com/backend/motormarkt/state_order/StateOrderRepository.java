package com.backend.motormarkt.state_order;

import com.backend.motormarkt.order.Order;
import com.backend.motormarkt.order_line.OrderLine;
import com.backend.motormarkt.state_order.StateOrder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateOrderRepository extends JpaRepository<StateOrder, Long> {
            List<StateOrder> findByOrder(Order order);

}
