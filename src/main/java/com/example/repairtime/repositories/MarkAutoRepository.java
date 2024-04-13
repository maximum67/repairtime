package com.example.repairtime.repositories;

import com.example.repairtime.models.MarkAuto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarkAutoRepository extends JpaRepository<MarkAuto,Long> {

    Optional<MarkAuto> findByName(String s);

//    Optional<List<MarkAuto>> findALL();
}
