package com.example.repairtime.repositories;

import com.example.repairtime.models.TypeRepair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TypeRepairRepository extends JpaRepository<TypeRepair, Long> {

    boolean existsByName(String string);

    Optional<TypeRepair> getByName(String string);
}
