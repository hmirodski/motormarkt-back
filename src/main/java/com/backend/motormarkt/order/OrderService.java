package com.backend.motormarkt.order;

import com.backend.motormarkt.emails.EmailService;
import com.backend.motormarkt.image_product.ImageProduct;
import com.backend.motormarkt.product.Product;
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

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StateRepository stateRepository;
    private final StateOrderRepository stateOrderRepository;
    private final EmailService emailService;
    private static final String CARACTERES_MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();



    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, StateRepository stateRepository, StateOrderRepository stateOrderRepository, EmailService emailService){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.stateRepository = stateRepository;
        this.stateOrderRepository = stateOrderRepository;
        this.emailService = emailService;
    }

    public List<Order> getAll(){
        return this.orderRepository.findAll();
    }

    public ResponseEntity<Object> getById(Long id) {
        HashMap<String, Object> data = new HashMap<>();

        Optional<Order> order = this.orderRepository.findById(id);
        if (order.isPresent()){
            data.put("data", order.get());
            return new ResponseEntity<>(
                    data,
                    HttpStatus.ACCEPTED
            );
        }else{
            data.put("error", true);
            data.put("message", "The order does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> save(Order order) {
    HashMap<String, Object> data = new HashMap<>();
    
    try {
        // Generar referencia y guardar la orden
        order.setReference(this.generarStringAleatorio());
        Order o = this.orderRepository.save(order);
        System.out.println("Orden guardada con ID: " + o.getId());
        
        // Asociar estado "Pago Aceptado"
        try {
            Optional<State> state = this.stateRepository.findByName("Pago Aceptado");
            System.out.println("Estado encontrado: " + (state.isPresent() ? "Sí" : "No"));
            
            if(state.isPresent()){
                StateOrder stateOrder = new StateOrder();
                stateOrder.setState(state.get());
                stateOrder.setOrder(o);
                this.stateOrderRepository.save(stateOrder);
                System.out.println("Estado de orden guardado correctamente");
            } else {
                System.out.println("No se encontró el estado 'Pago Aceptado'");
            }
        } catch(Exception e) {
            System.out.println("Error al crear estado de orden: " + e.getMessage());
            // Continuar con el proceso a pesar del error
        }
        
        // Enviar email
        try {
            Optional<User> user = this.userRepository.findById(order.getUser().getId());
            System.out.println("Usuario encontrado: " + (user.isPresent() ? "Sí" : "No"));
            
            if(user.isPresent()){
                emailService.sendEmail(user.get().getEmail(), "Pedido confirmado: " + order.getReference(), "Total: " + order.getTotal_price() + " €");
                System.out.println("Email enviado correctamente a: " + user.get().getEmail());
            }
        } catch(Exception e) {
            System.out.println("Error al enviar email: " + e.getMessage());
            e.printStackTrace();
            // Continuar con el proceso a pesar del error en el email
        }
        
        // Respuesta exitosa
        data.put("data", order);
        data.put("message", "Successfully saved");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
        
    } catch(Exception e) {
        System.out.println("Error general en save(): " + e.getMessage());
        e.printStackTrace();
        
        data.put("error", true);
        data.put("message", "Error al guardar la orden: " + e.getMessage());
        return new ResponseEntity<>(
                data,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

    public ResponseEntity<Object> update(Long id, Order order) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<Order> orderBD = this.orderRepository.findById(id);

        if(orderBD.isPresent()){
            if(order.getPayment() != null)
                orderBD.get().setPayment(order.getPayment());
            if(order.getTotal_price() != null)
                orderBD.get().setTotal_price(order.getTotal_price());

            this.orderRepository.save(orderBD.get());
            data.put("data", orderBD.get());
            data.put("message", "Successfully saved");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.CREATED
            );
        }else{
            data.put("error", true);
            data.put("message", "The order does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> delete(Long id){
        HashMap<String, Object> data = new HashMap<>();

        if(!this.orderRepository.existsById(id)){
            data.put("error", true);
            data.put("message", "The order does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        this.orderRepository.deleteById(id);
        data.put("message", "Order deleted");

        return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
        );
    }

    private String generarStringAleatorio() {
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int indice = RANDOM.nextInt(CARACTERES_MAYUSCULAS.length());
            char caracterAleatorio = CARACTERES_MAYUSCULAS.charAt(indice);
            sb.append(caracterAleatorio);
        }
        return sb.toString();
    }

    public ResponseEntity<Object> getOrdersForUser(Long user_id) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<User> userOptional = this.userRepository.findById(user_id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Order> orders = orderRepository.findByUser(user);

            return new ResponseEntity<>(
                orders,
                HttpStatus.ACCEPTED
            );
        } else {
            data.put("error", true);
            data.put("message", "The user does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

    }
}
