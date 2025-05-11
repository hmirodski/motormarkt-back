package com.backend.motormarkt.state;

import com.backend.motormarkt.state.State;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByName(String name);
}
