package com.backend.motormarkt.cart;

import com.backend.motormarkt.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository){
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public List<Cart> getAll(){
        return this.cartRepository.findAll();
    }

    public ResponseEntity<Object> getById(Long id) {
        HashMap<String, Object> data = new HashMap<>();

        Optional<Cart> cart = this.cartRepository.findById(id);
        if (cart.isPresent()){
            data.put("data", cart.get());
            return new ResponseEntity<>(
                    data,
                    HttpStatus.ACCEPTED
            );
        }else{
            data.put("error", true);
            data.put("message", "The cart does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> save(Cart cart) {
        HashMap<String, Object> data = new HashMap<>();

        this.cartRepository.save(cart);
        data.put("data", cart);
        data.put("message", "Successfully saved");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> update(Long id, Cart cart) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<Cart> cartBD = this.cartRepository.findById(id);

        if(cartBD.isPresent()){
            if(cart.getTotal_price() != null)
                cartBD.get().setTotal_price(cart.getTotal_price());

            this.cartRepository.save(cartBD.get());
            data.put("data", cartBD.get());
            data.put("message", "Successfully saved");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.CREATED
            );
        }else{
            data.put("error", true);
            data.put("message", "The cart does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> delete(Long id){
        HashMap<String, Object> data = new HashMap<>();

        if(!this.cartRepository.existsById(id)){
            data.put("error", true);
            data.put("message", "The cart does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        this.cartRepository.deleteById(id);
        data.put("message", "Cart deleted");

        return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
        );
    }
}
