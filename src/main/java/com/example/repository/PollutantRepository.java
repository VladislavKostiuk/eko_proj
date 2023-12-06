package com.example.repository;

import com.example.model.Object;
import com.example.model.Pollutant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PollutantRepository extends JpaRepository<Pollutant, Long> {
    Optional<Pollutant> findByName(String name);
}
