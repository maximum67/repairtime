package com.example.repairtime.repositories;

import com.example.repairtime.models.RepairGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepairGroupRepository extends JpaRepository<RepairGroup, Long> {

    boolean existsByName(String name);

    Optional<RepairGroup> getByName(String name);
}
