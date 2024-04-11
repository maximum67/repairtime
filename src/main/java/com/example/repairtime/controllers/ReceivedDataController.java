package com.example.repairtime.controllers;

import com.example.repairtime.models.ModificationAuto;
import com.example.repairtime.models.StandardTime;
import com.example.repairtime.repositories.StandardTimeRepository;
import com.example.repairtime.services.ModificationAutoService;
import com.example.repairtime.services.RepairDataService;
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

    private final RepairDataService repairDataService;
    private final ModificationAutoService modificationAutoService;
    private final StandardTimeRepository standardTimeRepository;

    @GetMapping("/modification")
    public String getModification(Model model){
        return "receivedDataPage";
    }

    @GetMapping("/modification/list")
    public String getModificationList(Model model){
        model.addAttribute("modificationList", repairDataService.modificationAutoList());
        return "repairDataList";
    }
    @GetMapping("/write")
    public String writeData() throws IOException {
        repairDataService.writingFileAndSave("test.xlsx");
        return "redirect:/data/modification/list";
    }
    @GetMapping("/modification/edit/{modification}")
    public String getModificationEdit(@PathVariable ("modification") ModificationAuto modificationAuto, Model model) {
        model.addAttribute("modification", modificationAuto);
        model.addAttribute("typeRepairs",
                standardTimeRepository.findDistinctByModificationAutoId(modificationAuto)
                        .stream().map(StandardTime::getTypeRepairId).collect(Collectors.toList()));
        return "modificationEdit";
    }
}
