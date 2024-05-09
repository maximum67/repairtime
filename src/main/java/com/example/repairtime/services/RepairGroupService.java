package com.example.repairtime.services;

import com.example.repairtime.models.ModificationAuto;
import com.example.repairtime.models.RepairGroup;
import com.example.repairtime.models.RepairGroupMain;
import com.example.repairtime.repositories.RepairGroupMainRepository;
import com.example.repairtime.repositories.RepairGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RepairGroupService {

    private final RepairGroupRepository repairGroupRepository;
    private final RepairGroupMainRepository repairGroupMainRepository;

    public List<RepairGroupMain> findAllRepairGroupMain() {
        return repairGroupMainRepository.findAll();
    }

    public List<Map<String, String>> getAllRepairGroupOfModification(RepairGroupMain repairGroupMain, ModificationAuto modificationAuto) {
        List<RepairGroup> repairGroupList = repairGroupMainRepository.getByName(repairGroupMain.getName()).get().getRepairGroupList();
        List<Map<String, String>> mapList = new LinkedList<>();
        for (RepairGroup rg : repairGroupList) {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("key", rg.getName());
            map.put("value", String.valueOf(rg.getId()));
            mapList.add(map);
        }
        return mapList;
    }
}