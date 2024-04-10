package com.example.repairtime.repositories;

import com.example.repairtime.models.ModelAuto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelAutoRepository extends JpaRepository<ModelAuto,Long> {
    ModelAuto findByName(String s);
}
