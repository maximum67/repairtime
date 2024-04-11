package com.example.repairtime.repositories;

import com.example.repairtime.models.RepairGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepairGroupRepository extends JpaRepository<RepairGroup, Long> {
}
