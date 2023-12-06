package com.example.repository;

import com.example.model.Object;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObjectRepository extends JpaRepository<Object, Long> {
    Optional<Object> findByName(String name);
}
