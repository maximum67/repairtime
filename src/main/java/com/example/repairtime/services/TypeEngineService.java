package com.example.repairtime.services;

import com.example.repairtime.comparators.TypeEngineComparator;
import com.example.repairtime.models.ModelAuto;
import com.example.repairtime.models.TypeEngine;
import com.example.repairtime.repositories.ModelAutoRepository;
import com.example.repairtime.repositories.TypeEngineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeEngineService {
    private final TypeEngineRepository typeEngineRepository;
    private final ModelAutoRepository modelAutoRepository;

    public List<TypeEngine> getTypeEngineList(Long modelAutoId){
        if (modelAutoRepository.findById(modelAutoId).isPresent()) {
            return modelAutoRepository.findById(modelAutoId).get().getTypeEngineList().stream().sorted(new TypeEngineComparator()).toList();
        } else {
            return new LinkedList<>();
        }

    }
}
