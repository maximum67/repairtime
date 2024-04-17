package com.example.repairtime.controllers;

import com.example.repairtime.models.*;
import com.example.repairtime.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/select")
public class RestDataController {

    private final RepairDataService repairDataService;
    private final ModificationAutoService modificationAutoService;
    private final StandardTimeService standardTimeService;
    private final MarkAutoService markAutoService;
    private final ModelAutoService modelAutoService;
    private final TypeEngineService typeEngineService;

    @GetMapping("/grouprepair/{markid}/{modelid}/{typeEngineid}/{modificationid}/{id}")
    public List<Map<String, String>> getTypeRepair(@PathVariable("markid") MarkAuto markAuto,
                                   @PathVariable("modelid") ModelAuto modelAuto,
                                   @PathVariable("typeEngineid") TypeEngine typeEngine,
                                   @PathVariable("modificationid") ModificationAuto modificationAuto,
                                   @PathVariable("id") RepairGroup repairGroup) {
        return standardTimeService.getMapDataStandardTime(modificationAuto,repairGroup);
//        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
