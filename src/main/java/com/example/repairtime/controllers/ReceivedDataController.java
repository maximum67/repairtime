package com.example.repairtime.controllers;

import com.example.repairtime.models.MarkAuto;
import com.example.repairtime.models.ModificationAuto;
import com.example.repairtime.models.StandardTime;
import com.example.repairtime.repositories.StandardTimeRepository;
import com.example.repairtime.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/data")
public class ReceivedDataController {

   
    private final ModificationAutoService modificationAutoService;
    private final StandardTimeService standardTimeService;
    private final MarkAutoService markAutoService;
    private final ModelAutoService modelAutoService;
    private final AutoDataService autoDataService;
    private final RepairElementService repairElementService;
    private final RepairGroupService repairGroupService;


    @GetMapping("/modification/list")
    public String getModificationList(Model model){
        model.addAttribute("modificationList", modificationAutoService.modificationAutoList());
        return "repairDataList";
    }
    @GetMapping("/write")
    public String writeData() throws IOException {
//        repairDataService.writingFileAndSave("test.xlsx");
//        autoDataService.readFileAndSaveData("test.xlsx");
//        repairElementService.readFile("6_ЭлементыРемонтныхРабот.txt");

        repairGroupService.readFileGroup("5_ГруппыРемонтныхРабот.txt");
        return "redirect:/data/modification/list";
    }
    @GetMapping("/modification/edit/{modification}")
    public String getModificationEdit(@PathVariable ("modification") ModificationAuto modificationAuto, Model model) {
        model.addAttribute("modification", modificationAuto);
//        model.addAttribute("typeRepairs",
//                standardTimeService.getStandardTimeListByModification(modificationAuto)
//                        .stream().map(StandardTime::getTypeRepairId).collect(Collectors.toList()));
        model.addAttribute("standardTimes",
                standardTimeService.getStandardTimeListByModification(modificationAuto));
//                        .stream().map(StandardTime::getStandardTime).collect(Collectors.toList()));
        return "modificationEdit";
    }
    @GetMapping("/modification")
    public String getModification(Model model){
        return "receivedDataPage";
    }
}
