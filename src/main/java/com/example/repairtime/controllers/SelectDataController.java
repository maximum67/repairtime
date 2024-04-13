package com.example.repairtime.controllers;

import com.example.repairtime.models.MarkAuto;
import com.example.repairtime.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/select")
public class SelectDataController {

    private final RepairDataService repairDataService;
    private final ModificationAutoService modificationAutoService;
    private final StandardTimeService standardTimeService;
    private final MarkAutoService markAutoService;
    private final ModelAutoService modelAutoService;

    @GetMapping("/mark")
    public String getMark(Model model){
        model.addAttribute("isPermission", false);
        model.addAttribute("isPermission1", false);
        model.addAttribute("isPermission2", false);
        model.addAttribute("markList",
                markAutoService.getMarkAutoList());
        return "markList";
    }
    @GetMapping("/mark/{id}")
    public String getModel(@PathVariable("id") MarkAuto markAuto, Model model){
        model.addAttribute("isPermission", true);
        model.addAttribute("isPermission1", false);
        model.addAttribute("isPermission2", false);
        model.addAttribute("mark", markAuto);
        model.addAttribute("modelsList",
                modelAutoService.getModelsListByMark(markAuto.getId()));
        return "modelsList";
    }

    @GetMapping("/modification")
    public String getModification(Model model){
        return "receivedDataPage";
    }

}
