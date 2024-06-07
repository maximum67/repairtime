package com.example.repairtime.repositories;

import com.example.repairtime.models.SpecificationRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecificationRowRepository extends JpaRepository<SpecificationRow, Long> {


}
