package com.example.repairtime.controllers;

import com.example.repairtime.models.*;
import com.example.repairtime.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    private final SpecificationsCarService specificationsCarService;

    @GetMapping("/standardTime/{markid}/{modelid}/{typeEngineid}/{modificationid}/{id}")
    public List<Map<String, String>> getTypeRepair(@PathVariable("markid") MarkAuto markAuto,
                                   @PathVariable("modelid") ModelAuto modelAuto,
                                   @PathVariable("typeEngineid") TypeEngine typeEngine,
                                   @PathVariable("modificationid") ModificationAuto modificationAuto,
                                   @PathVariable("id") RepairGroup repairGroup) throws NoSuchPaddingException,
                                                                                       IllegalBlockSizeException,
                                                                                       NoSuchAlgorithmException,
                                                                                       BadPaddingException,
                                                                                       InvalidKeyException {
        return standardTimeService.getMapDataStandardTime(modificationAuto, repairGroup);
    }

    @GetMapping("/groupRepair/{markid}/{modelid}/{typeEngineid}/{modificationid}/{id}")
        public List<Map<String, String>> getRepairGroup(@PathVariable("markid") MarkAuto markAuto,
                                                @PathVariable("modelid") ModelAuto modelAuto,
                                                @PathVariable("typeEngineid") TypeEngine typeEngine,
                                                @PathVariable("modificationid") ModificationAuto modificationAuto,
                                                @PathVariable("id") RepairGroupMain repairGroupMain){
        return  standardTimeService.getAllRepairGroupOfModification(repairGroupMain,modificationAuto);
    }

    @GetMapping("/specificationGroup/{id}")
    public List<Map<String, String>> getSpecificationGroup(@PathVariable("id") SpecificationGroup specificationGroup){
        return specificationsCarService.getSpecificationsMaps(specificationGroup);
    }
}
