package com.example.repairtime.services;

import com.example.repairtime.models.TypeRepair;
import com.example.repairtime.repositories.TypeRepairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TypeRepaireService {
    private final TypeRepairRepository typeRepairRepository;

    public TypeRepair getTypeRepairByVendorCode(String vendorCode){
       return typeRepairRepository.getByVendorCode(vendorCode).get();
        }

}
