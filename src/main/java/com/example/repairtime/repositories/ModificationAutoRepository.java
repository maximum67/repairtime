package com.example.repairtime.repositories;

import com.example.repairtime.models.ModificationAuto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ModificationAutoRepository extends JpaRepository<ModificationAuto,Long> {

    ModificationAuto getReferenceById(Long id);

}
