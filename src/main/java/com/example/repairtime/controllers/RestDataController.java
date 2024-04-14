package com.example.repairtime.controllers;

import com.example.repairtime.models.*;
import com.example.repairtime.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<List> getTypeRepair(@PathVariable("markid") MarkAuto markAuto,
                                @PathVariable("modelid") ModelAuto modelAuto,
                                @PathVariable("typeEngineid") TypeEngine typeEngine,
                                @PathVariable("modificationid") ModificationAuto modificationAuto,
                                @PathVariable("id") RepairGroup repairGroup) {
        List<Map> list = standardTimeService.getMapDataStandardTime(modificationAuto,repairGroup);
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }
}
