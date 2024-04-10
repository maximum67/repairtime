package com.example.repairtime.controllers;

import com.example.repairtime.services.RepairDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/data")
public class ReceivedDataController {

    private final RepairDataService repairDataService;

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

}
