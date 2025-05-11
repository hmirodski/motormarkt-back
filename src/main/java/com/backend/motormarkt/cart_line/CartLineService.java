package com.backend.motormarkt.cart_line;

import com.backend.motormarkt.cart.CartRepository;
import com.backend.motormarkt.cart_line.CartLine;
import com.backend.motormarkt.cart_line.CartLineRepository;
import com.backend.motormarkt.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CartLineService {
    private final CartLineRepository cartLineRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartLineService(CartLineRepository cartLineRepository, CartRepository cartRepository, UserRepository userRepository){
        this.cartLineRepository = cartLineRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public List<CartLine> getAll(){
        return this.cartLineRepository.findAll();
    }

    public ResponseEntity<Object> getById(Long id) {
        HashMap<String, Object> data = new HashMap<>();

        Optional<CartLine> cartLine = this.cartLineRepository.findById(id);
        if (cartLine.isPresent()){
            data.put("data", cartLine.get());
            return new ResponseEntity<>(
                    data,
                    HttpStatus.ACCEPTED
            );
        }else{
            data.put("error", true);
            data.put("message", "The cartLine does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> save(CartLine cartLine) {
        HashMap<String, Object> data = new HashMap<>();

        System.out.println(cartLine);
        this.cartLineRepository.save(cartLine);
        data.put("data", cartLine);
        data.put("message", "Successfully saved");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> update(Long id, CartLine cartLine) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<CartLine> cartLineBD = this.cartLineRepository.findById(id);

        if(cartLineBD.isPresent()){
            if(cartLine.getPrice_unit() != null)
                cartLineBD.get().setPrice_unit(cartLine.getPrice_unit());

            if(cartLine.getTotal_price() != null)
                cartLineBD.get().setTotal_price(cartLine.getTotal_price());

            if(cartLine.getQuantity() != null)
                cartLineBD.get().setQuantity(cartLine.getQuantity());

            this.cartLineRepository.save(cartLineBD.get());
            data.put("data", cartLineBD.get());
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

        if(!this.cartLineRepository.existsById(id)){
            data.put("error", true);
            data.put("message", "The orderLine does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        this.cartLineRepository.deleteById(id);
        data.put("message", "OrderLine deleted");

        return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
        );
    }
}
