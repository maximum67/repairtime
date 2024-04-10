package com.example.repairtime.repositories;

import com.example.repairtime.models.ModificationAuto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ModificationAutoRepository extends JpaRepository<ModificationAuto,Long> {

    Optional<ModificationAuto> findByName(String s);
}
