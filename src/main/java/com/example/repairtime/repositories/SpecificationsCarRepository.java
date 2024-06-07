package com.example.repairtime.repositories;

import com.example.repairtime.models.SpecificationsCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecificationsCarRepository extends JpaRepository<SpecificationsCar, Long> {

    SpecificationsCar getByRepairCode(String string);

    Optional<SpecificationsCar> getOptionalByRepairCode(String string);

    Optional<SpecificationsCar> findAllByRepairCode(String string);

    boolean existsByRepairCode(String string);
}
