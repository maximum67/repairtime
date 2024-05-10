package com.example.repairtime.repositories;

import com.example.repairtime.models.SpecificationsGroupName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpecificationsGroupNameRepository extends JpaRepository<SpecificationsGroupName,Long> {

}
