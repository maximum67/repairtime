package com.example.repairtime.repositories;

import com.example.repairtime.models.StandardTime;
import com.example.repairtime.models.StandardTimeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandardTimeRepository extends JpaRepository<StandardTime, StandardTimeKey> {
}
