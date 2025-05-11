package com.backend.motormarkt.order_line;

import com.backend.motormarkt.order_line.OrderLine;
import com.backend.motormarkt.order_line.OrderLineRepository;
import com.backend.motormarkt.product.Product;
import com.backend.motormarkt.product.ProductRepository;
import com.backend.motormarkt.state.State;
import com.backend.motormarkt.order.Order;
import com.backend.motormarkt.order.OrderRepository;
import com.backend.motormarkt.user.User;
import com.backend.motormarkt.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderLineService(OrderLineRepository orderLineRepository, OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository){
        this.orderLineRepository = orderLineRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository; 
    }

    public List<OrderLine> getAll(){
        return this.orderLineRepository.findAll();
    }

    public ResponseEntity<Object> getById(Long id) {
        HashMap<String, Object> data = new HashMap<>();

        Optional<OrderLine> orderLine = this.orderLineRepository.findById(id);
        if (orderLine.isPresent()){
            data.put("data", orderLine.get());
            return new ResponseEntity<>(
                    data,
                    HttpStatus.ACCEPTED
            );
        }else{
            data.put("error", true);
            data.put("message", "The orderLine does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> save(OrderLine orderLine) {
        HashMap<String, Object> data = new HashMap<>();

        this.orderLineRepository.save(orderLine);

        // Quitamos stock a los productos
        Optional<Product> product = this.productRepository.findById(orderLine.getProduct().getId());
        if(product.isPresent()){
            product.get().setQuantity(product.get().getQuantity() - orderLine.getQuantity());
            this.productRepository.save(product.get());
        }
        
        data.put("data", orderLine);
        data.put("message", "Successfully saved");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> update(Long id, OrderLine orderLine) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<OrderLine> orderLineBD = this.orderLineRepository.findById(id);

        if(orderLineBD.isPresent()){
            if(orderLine.getPrice_unit() != null)
                orderLineBD.get().setPrice_unit(orderLine.getPrice_unit());

            if(orderLine.getTotal_price() != null)
                orderLineBD.get().setTotal_price(orderLine.getTotal_price());

            if(orderLine.getQuantity() != null)
                orderLineBD.get().setQuantity(orderLine.getQuantity());

            this.orderLineRepository.save(orderLineBD.get());
            data.put("data", orderLineBD.get());
            data.put("message", "Successfully saved");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.CREATED
            );
        }else{
            data.put("error", true);
            data.put("message", "The orderLine does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> delete(Long id){
        HashMap<String, Object> data = new HashMap<>();

        if(!this.orderLineRepository.existsById(id)){
            data.put("error", true);
            data.put("message", "The orderLine does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        this.orderLineRepository.deleteById(id);
        data.put("message", "OrderLine deleted");

        return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
        );
    }

    public ResponseEntity<Object> getOrderLinesForOrder(Long order_id) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<Order> orderOptional = this.orderRepository.findById(order_id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<OrderLine> orderLines = orderLineRepository.findByOrder(order);
            //data.put("data", orderLines);

            return new ResponseEntity<>(
                orderLines,
                HttpStatus.ACCEPTED
            );
        } else {
            data.put("error", true);
            data.put("message", "The order does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

    }
}
