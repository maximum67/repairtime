package com.example.repairtime.services;

import com.example.repairtime.models.ModificationAuto;
import com.example.repairtime.repositories.ModificationAutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModificationAutoService {
    private final ModificationAutoRepository modificationAutoRepository;

    public ModificationAuto modificationAutoList(Long id){
        return modificationAutoRepository.getReferenceById(id);
    }

    public List<ModificationAuto> modificationAutoList(){
        return modificationAutoRepository.findAll();
    }
}
