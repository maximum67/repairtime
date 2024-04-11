package com.example.repairtime.repositories;

import com.example.repairtime.models.TypeEngine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TypeEngineRepository extends JpaRepository<TypeEngine,Long> {

    Optional<List<TypeEngine>> findAllByName(String s);
}
