package com.example.repairtime.repositories;

import com.example.repairtime.models.ModificationAuto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ModificationAutoRepository extends JpaRepository<ModificationAuto,Long> {

    ModificationAuto findByName(String s);

}
