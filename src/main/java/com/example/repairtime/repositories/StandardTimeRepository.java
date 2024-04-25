package com.example.repairtime.repositories;

import com.example.repairtime.models.ModificationAuto;
import com.example.repairtime.models.StandardTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StandardTimeRepository extends JpaRepository<StandardTime, Long> {

    StandardTime getByRepairCode(String repairCode);

    boolean existsByRepairCode(String repairCode);

}
