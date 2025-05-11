package com.backend.motormarkt.state;

import com.backend.motormarkt.state.State;
import com.backend.motormarkt.state.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/states")
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class StateController {
    private final StateService stateService;

    @Autowired
    public StateController(StateService stateService){
        this.stateService = stateService;
    }

    @GetMapping
    public List<State> getAll(){
        return this.stateService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) { return this.stateService.getById(id); }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody State state){
        return this.stateService.save(state);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody State order){
        return this.stateService.update(id, order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return this.stateService.delete(id);
    }
}
