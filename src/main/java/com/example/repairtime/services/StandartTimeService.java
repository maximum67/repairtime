package com.example.repairtime.services;

import com.example.repairtime.models.ModificationAuto;
import com.example.repairtime.models.StandardTime;
import com.example.repairtime.repositories.StandardTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StandartTimeService {

    private final StandardTimeRepository standardTimeRepository;

    List<StandardTime> getStandardTimeListByModification(ModificationAuto modificationAuto){
        return standardTimeRepository.findDistinctByModificationAutoId(modificationAuto);
    }
}
