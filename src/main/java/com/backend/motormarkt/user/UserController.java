package com.backend.motormarkt.user;

import com.backend.motormarkt.category.Category;
import com.backend.motormarkt.category.CategoryService;
import com.backend.motormarkt.user.User;
import com.backend.motormarkt.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll(){
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) { return this.userService.getById(id); }

    @GetMapping("/email/{email}")
    public ResponseEntity<Object> getByEmail(@PathVariable String email) { return this.userService.getByEmail(email); }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody User user){
        return this.userService.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody User user){
        return this.userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return this.userService.delete(id);
    }

    @PostMapping("/login_admin")
    public ResponseEntity<Object> login_admin(@RequestBody User user){
        return this.userService.login_admin(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user){
        return this.userService.login(user);
    }
}
