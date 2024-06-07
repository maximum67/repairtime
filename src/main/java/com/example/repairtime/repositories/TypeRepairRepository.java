package com.example.repairtime.repositories;

import com.example.repairtime.models.TypeRepair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TypeRepairRepository extends JpaRepository<TypeRepair, Long> {

    boolean existsByVendorCode(String vendorCode);

    Optional<TypeRepair> getByName(String string);

    Optional<TypeRepair> getByVendorCode(String string);
}
