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
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/select")
public class RestDataController {

    private final StandardTimeService standardTimeService;
    private final SpecificationsCarService specificationsCarService;

    @GetMapping("/standardTime/{modificationid}/{id}")
    public List<Map<String, String>> getTypeRepair(@PathVariable("modificationid") ModificationAuto modificationAuto,
                                                   @PathVariable("id") RepairGroup repairGroup) throws NoSuchPaddingException,
                                                       IllegalBlockSizeException, NoSuchAlgorithmException,
                                                       BadPaddingException, InvalidKeyException {
        return standardTimeService.getMapDataStandardTime(modificationAuto, repairGroup);
    }

    @GetMapping("/groupRepair/{modificationid}/{id}")
        public List<Map<String, String>> getRepairGroup(@PathVariable("modificationid") ModificationAuto modificationAuto,
                                                        @PathVariable("id") RepairGroupMain repairGroupMain) throws NoSuchPaddingException,
                                                                               IllegalBlockSizeException, NoSuchAlgorithmException,
                                                                               BadPaddingException, InvalidKeyException {
        return  standardTimeService.getAllRepairGroupsOfModification(repairGroupMain,modificationAuto);
    }

    @GetMapping("/specificationGroup/{id}")
    public List<Map<String, String>> getSpecificationGroup(@PathVariable("id") SpecificationGroup specificationGroup){
        return specificationsCarService.getSpecificationsMaps(specificationGroup);
    }
}
