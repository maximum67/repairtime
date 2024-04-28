package com.example.repairtime.controllers;

import com.example.repairtime.models.*;
import com.example.repairtime.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/select")
public class RestDataController {

    private final ModificationAutoService modificationAutoService;
    private final StandardTimeService standardTimeService;
    private final MarkAutoService markAutoService;
    private final ModelAutoService modelAutoService;
    private final TypeEngineService typeEngineService;
    private final RepairGroupService repairGroupService;

    @GetMapping("/standardTime/{markid}/{modelid}/{typeEngineid}/{modificationid}/{id}")
    public List<Map<String, String>> getTypeRepair(@PathVariable("markid") MarkAuto markAuto,
                                   @PathVariable("modelid") ModelAuto modelAuto,
                                   @PathVariable("typeEngineid") TypeEngine typeEngine,
                                   @PathVariable("modificationid") ModificationAuto modificationAuto,
                                   @PathVariable("id") RepairGroup repairGroup) {

        return new ArrayList<>();
    }

    @GetMapping("/groupRepair/{markid}/{modelid}/{typeEngineid}/{modificationid}/{id}")
        public List<Map<String, String>> getRepairGroup(@PathVariable("markid") MarkAuto markAuto,
                                                @PathVariable("modelid") ModelAuto modelAuto,
                                                @PathVariable("typeEngineid") TypeEngine typeEngine,
                                                @PathVariable("modificationid") ModificationAuto modificationAuto,
                                                @PathVariable("id") RepairGroupMain repairGroupMain){
         List<Map<String,String>> mapList = new LinkedList<>();
         Map<String,String> map = repairGroupService.getAllRepairGroupOfModification(repairGroupMain,modificationAuto);
         mapList.add(map);
        System.out.println(mapList.toString());
        return mapList;
    }
}
