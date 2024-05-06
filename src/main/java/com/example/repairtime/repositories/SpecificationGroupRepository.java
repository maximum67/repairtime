package com.example.repairtime.repositories;

import com.example.repairtime.models.SpecificationGroup;
import com.example.repairtime.models.SpecificationsCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpecificationGroupRepository extends JpaRepository<SpecificationGroup, Long> {
    Optional<List<SpecificationGroup>> findAllBySpecificationsCar_RepairCode(String string);
}
