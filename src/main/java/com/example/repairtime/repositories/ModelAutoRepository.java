package com.example.repairtime.repositories;

import com.example.repairtime.models.ModelAuto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelAutoRepository extends JpaRepository<ModelAuto,Long> {
    Optional<ModelAuto> findByName(String s);
}
