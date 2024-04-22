package com.example.repairtime.services;

import com.example.repairtime.models.ModificationAuto;
import com.example.repairtime.models.RepairGroup;
import com.example.repairtime.models.StandardTime;
import com.example.repairtime.models.TypeRepair;
import com.example.repairtime.repositories.StandardTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StandardTimeService {

    private final StandardTimeRepository standardTimeRepository;

    public List<StandardTime> getStandardTimeListByModification(ModificationAuto modificationAuto){
        return standardTimeRepository.findDistinctByModificationAutoId(modificationAuto);
    }


}
