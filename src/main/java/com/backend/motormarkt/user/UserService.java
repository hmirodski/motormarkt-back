package com.backend.motormarkt.user;

import com.backend.motormarkt.category.Category;
import com.backend.motormarkt.category.CategoryRepository;
import com.backend.motormarkt.user.User;
import com.backend.motormarkt.user.UserRepository;
import com.backend.motormarkt.utils.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAll(){
        return this.userRepository.findAll();
    }

    public ResponseEntity<Object> getById(Long id) {
        HashMap<String, Object> data = new HashMap<>();

        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()){
            data.put("data", user.get());
            return new ResponseEntity<>(
                    data,
                    HttpStatus.ACCEPTED
            );
        }else{
            data.put("error", true);
            data.put("message", "The user does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> save(User user) {
        HashMap<String, Object> data = new HashMap<>();

        if(user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getName().isEmpty()){
            data.put("error", true);
            data.put("message", "Faltan campos requeridos");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        this.userRepository.save(user);
        data.put("data", user);
        data.put("message", "Successfully saved");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> delete(Long id){
        HashMap<String, Object> data = new HashMap<>();

        if(!this.userRepository.existsById(id)){
            data.put("error", true);
            data.put("message", "The user does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        this.userRepository.deleteById(id);
        data.put("message", "User deleted");

        return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
        );
    }

    public ResponseEntity<Object> update(Long id, User user) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<User> userBD = this.userRepository.findById(id);

        if(userBD.isPresent()){
            if(user.getName() != null)
                userBD.get().setName(user.getName());

            if (user.getEmail() != null) {
                userBD.get().setEmail(user.getEmail());
            }

            if (user.getPassword() != null) {
                userBD.get().setPassword(user.getPassword());
            }

            if (user.getAddress1() != null) {
                userBD.get().setAddress1(user.getAddress1());
            }

            if (user.getAddress2() != null) {
                userBD.get().setAddress2(user.getAddress2());
            }

            if (user.getCity() != null) {
                userBD.get().setCity(user.getCity());
            }

            if (user.getPostcode() != null) {
                userBD.get().setPostcode(user.getPostcode());
            }

            if (user.getCountry() != null) {
                userBD.get().setCountry(user.getCountry());
            }

            if (user.getPhone() != null) {
                userBD.get().setPhone(user.getPhone());
            }

            if (user.isActive() != null) {
                userBD.get().setActive(user.isActive());
            }

            if (user.isAdmin() != null) {
                userBD.get().setAdmin(user.isAdmin());
            }

            if (user.isClient() != null) {
                userBD.get().setClient(user.isClient());
            }

            this.userRepository.save(userBD.get());
            data.put("data", userBD.get());
            data.put("message", "Successfully saved");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.CREATED
            );
        }else{
            data.put("error", true);
            data.put("message", "The user does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> login(User user) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<User> userBD = this.userRepository.findUserByEmail(user.getEmail());
        if (userBD.isPresent() && userBD.get().isActive() && userBD.get().isClient()){
            if(BCrypt.checkpw(user.getPassword(), userBD.get().getPassword())){
                data.put("data", userBD.get());
                return new ResponseEntity<>(
                        data,
                        HttpStatus.ACCEPTED
                );
            }else{
                data.put("error", true);
                data.put("message", "The user does not exist");

                return new ResponseEntity<>(
                        data,
                        HttpStatus.CONFLICT
                );
            }
        }else{
            data.put("error", true);
            data.put("message", "The user does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> login_admin(User user) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<User> userBD = this.userRepository.findUserByEmail(user.getEmail());
        if (userBD.isPresent() && userBD.get().isAdmin() && userBD.get().isActive()){
            if(BCrypt.checkpw(user.getPassword(), userBD.get().getPassword())){
                data.put("data", userBD.get());
                return new ResponseEntity<>(
                        data,
                        HttpStatus.ACCEPTED
                );
            }else{
                data.put("error", true);
                data.put("message", "The user does not exist");

                return new ResponseEntity<>(
                        data,
                        HttpStatus.CONFLICT
                );
            }
        }else{
            data.put("error", true);
            data.put("message", "The user does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> getByEmail(String email) {
        HashMap<String, Object> data = new HashMap<>();

        Optional<User> user = this.userRepository.findUserByEmail(email);
        if (user.isPresent()){
            data.put("data", user.get());
            return new ResponseEntity<>(
                    data,
                    HttpStatus.ACCEPTED
            );
        }else{
            data.put("error", true);
            data.put("message", "The user does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }
}
