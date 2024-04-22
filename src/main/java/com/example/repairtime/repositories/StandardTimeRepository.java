package com.example.repairtime.repositories;

import com.example.repairtime.models.ModificationAuto;
import com.example.repairtime.models.StandardTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StandardTimeRepository extends JpaRepository<StandardTime, Long> {
    List<StandardTime> findDistinctByModificationAutoId(ModificationAuto modificationAutoId);

}
