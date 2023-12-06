package com.example.repository;

import com.example.model.Object;
import com.example.model.Pollutant;
import com.example.model.Pollution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollutionRepository extends JpaRepository<Pollution, Long> {
    void deleteByObject(Object object);
    void deleteByPollutant(Pollutant pollutant);
}
