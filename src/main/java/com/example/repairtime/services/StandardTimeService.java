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

    public List<StandardTime> getStandardTimeListByModificationAngRepairGroup(ModificationAuto modificationAuto,
                                                                              RepairGroup repairGroup){
        List<StandardTime> standardTimeList  = getStandardTimeListByModification(modificationAuto);
        List<StandardTime> list = new LinkedList<>();
        for (StandardTime st: standardTimeList) {
            if (st.getTypeRepairId().getRepairGroup().equals(repairGroup)){
                list.add(st);
            }
        }
            return list;
    }
    public List<Map> getMapDataStandardTime(ModificationAuto modificationAuto, RepairGroup repairGroup){
        List<String> typeRepairList = getStandardTimeListByModificationAngRepairGroup(modificationAuto, repairGroup)
                .stream().map(StandardTime::getTypeRepairId).map(TypeRepair::getName).toList();
        List<Double> standardTimeList = getStandardTimeListByModificationAngRepairGroup(modificationAuto, repairGroup)
                .stream().map(StandardTime::getStandardTime).toList();
        Map<String, Double> map = new HashMap<>();
        for (int i=0; i< typeRepairList.size(); i++) {
            map.put(typeRepairList.get(i), standardTimeList.get(i));
        }
        List<Map> mapList = new LinkedList<>();
        mapList.add(map);
        return mapList;
    }
}
