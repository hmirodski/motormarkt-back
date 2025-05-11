package com.backend.motormarkt.state_order;

import com.backend.motormarkt.emails.EmailService;
import com.backend.motormarkt.order.Order;
import com.backend.motormarkt.order.OrderRepository;
import com.backend.motormarkt.order_line.OrderLine;
import com.backend.motormarkt.state.State;
import com.backend.motormarkt.state.StateRepository;
import com.backend.motormarkt.state_order.StateOrder;
import com.backend.motormarkt.state_order.StateOrderRepository;
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
public class StateOrderService {
    private final StateOrderRepository stateOrderRepository;
    private final OrderRepository orderRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Autowired
    public StateOrderService(StateOrderRepository stateOrderRepository, OrderRepository orderRepository, StateRepository stateRepository, UserRepository userRepository, EmailService emailService){
        this.stateOrderRepository = stateOrderRepository;
        this.orderRepository = orderRepository;
        this.stateRepository = stateRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public List<StateOrder> getAll(){
        return this.stateOrderRepository.findAll();
    }

    public ResponseEntity<Object> getById(Long id) {
        HashMap<String, Object> data = new HashMap<>();

        Optional<StateOrder> stateOrder = this.stateOrderRepository.findById(id);
        if (stateOrder.isPresent()){
            data.put("data", stateOrder.get());
            return new ResponseEntity<>(
                    data,
                    HttpStatus.ACCEPTED
            );
        }else{
            data.put("error", true);
            data.put("message", "The stateOrder does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> save(StateOrder stateOrder) {
        HashMap<String, Object> data = new HashMap<>();

        this.stateOrderRepository.save(stateOrder);

        Optional<Order> order = this.orderRepository.findById(stateOrder.getOrder().getId());
        if(order.isPresent()){
            Optional<User> user = this.userRepository.findById(order.get().getUser().getId());
            if(user.isPresent()){
                Optional<State> state = this.stateRepository.findById(stateOrder.getState().getId());
                if(state.isPresent()){
                    emailService.sendEmail(user.get().getEmail(), "Nuevo estado de tu pedido: " + order.get().getReference(), "Tu pedido se encuentra en estado: " + state.get().getName());
                }
            }
        }

        data.put("data", stateOrder);
        data.put("message", "Successfully saved");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> delete(Long id){
        HashMap<String, Object> data = new HashMap<>();

        if(!this.stateOrderRepository.existsById(id)){
            data.put("error", true);
            data.put("message", "The stateOrder does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        this.stateOrderRepository.deleteById(id);
        data.put("message", "StateOrder deleted");

        return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
        );
    }

    public ResponseEntity<Object> getStatesOrderForOrder(Long order_id) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<Order> orderOptional = this.orderRepository.findById(order_id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<StateOrder> statesOrderLines = stateOrderRepository.findByOrder(order);
            //data.put("data", statesOrderLines);

            return new ResponseEntity<>(
                statesOrderLines,
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
