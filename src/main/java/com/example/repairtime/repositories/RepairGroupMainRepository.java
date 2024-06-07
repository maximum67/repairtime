package com.example.repairtime.repositories;

import com.example.repairtime.models.RepairGroupMain;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RepairGroupMainRepository extends JpaRepository<RepairGroupMain, Long> {

    boolean existsByName(String name);

    Optional<RepairGroupMain>  getByName(String name);
}
