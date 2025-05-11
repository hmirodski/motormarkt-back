package com.backend.motormarkt.state;

import com.backend.motormarkt.state.State;
import com.backend.motormarkt.state.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class StateService {
    private final StateRepository stateRepository;

    @Autowired
    public StateService(StateRepository stateRepository){
        this.stateRepository = stateRepository;
    }

    public List<State> getAll(){
        return this.stateRepository.findAll();
    }

    public ResponseEntity<Object> getById(Long id) {
        HashMap<String, Object> data = new HashMap<>();

        Optional<State> state = this.stateRepository.findById(id);
        if (state.isPresent()){
            data.put("data", state.get());
            return new ResponseEntity<>(
                    data,
                    HttpStatus.ACCEPTED
            );
        }else{
            data.put("error", true);
            data.put("message", "The state does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> save(State state) {
        HashMap<String, Object> data = new HashMap<>();

        this.stateRepository.save(state);
        data.put("data", state);
        data.put("message", "Successfully saved");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> delete(Long id){
        HashMap<String, Object> data = new HashMap<>();

        if(!this.stateRepository.existsById(id)){
            data.put("error", true);
            data.put("message", "The state does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        this.stateRepository.deleteById(id);
        data.put("message", "State deleted");

        return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
        );
    }


    public ResponseEntity<Object> update(Long id, State state) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<State> stateBD = this.stateRepository.findById(id);

        if(stateBD.isPresent()){
            if(state.getName() != null)
                stateBD.get().setName(state.getName());

            if(state.getColor() != null)
                stateBD.get().setColor(state.getColor());

            this.stateRepository.save(stateBD.get());
            data.put("data", stateBD.get());
            data.put("message", "Successfully saved");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.CREATED
            );
        }else{
            data.put("error", true);
            data.put("message", "The state does not exist");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
    }
}
