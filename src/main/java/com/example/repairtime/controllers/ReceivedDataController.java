package com.example.repairtime.controllers;

import com.example.repairtime.services.ModificationAutoService;
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

    private final ModificationAutoService modificationAutoService;


    @GetMapping("/modification")
    public String getModification(Model model){
        return "receivedDataPage";
    }

    @GetMapping("/modification/list")
    public String getModificationList(Model model){
        model.addAttribute("modificationList",modificationAutoService.modificationAutoList());
        return "repairDataList";
    }
    @GetMapping("/write")
    public String writeData() throws IOException {
        modificationAutoService.writingFileAndSave("test.xlsx");
        return "redirect:/data/modification/list";
    }

}
