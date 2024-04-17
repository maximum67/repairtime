package com.example.repairtime.services;

import com.example.repairtime.comparators.ModelAutoComparator;
import com.example.repairtime.models.ModelAuto;
import com.example.repairtime.repositories.MarkAutoRepository;
import com.example.repairtime.repositories.ModelAutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModelAutoService {

    private final ModelAutoRepository modelAutoRepository;
    private final MarkAutoRepository markAutoRepository;

    public List<ModelAuto> getModelsListByMark(Long markId) {

        if (markAutoRepository.findById(markId).isPresent()) {
            return markAutoRepository.findById(markId).get().getModelAutoList().stream().sorted(new ModelAutoComparator()).toList();
        } else {
            return new LinkedList<>();
        }
    }
}
