package com.example.repairtime.controllers;

import com.example.repairtime.models.*;
import com.example.repairtime.repositories.TypeEngineRepository;
import com.example.repairtime.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/select")
public class SelectDataController {

    private final RepairDataService repairDataService;
    private final ModificationAutoService modificationAutoService;
    private final StandardTimeService standardTimeService;
    private final MarkAutoService markAutoService;
    private final ModelAutoService modelAutoService;
    private final TypeEngineService typeEngineService;

    @GetMapping("/mark")
    public String getMark(Model model){
        model.addAttribute("markList",
                markAutoService.getMarkAutoList());
        return "markList";
    }

    @GetMapping("/mark/{id}")
    public String getModel(@PathVariable("id") MarkAuto markAuto, Model model){
        model.addAttribute("mark", markAuto);
        model.addAttribute("modelsList",
                modelAutoService.getModelsListByMark(markAuto.getId()));
        return "modelsList";
    }

    @GetMapping("/model/{markid}/{id}")
    public String getTypeEngine(@PathVariable("markid") MarkAuto markAuto,
                                @PathVariable("id") ModelAuto modelAuto, Model model) {
        model.addAttribute("mark", markAuto);
        model.addAttribute("model", modelAuto);
        model.addAttribute("typeEngineList",
                typeEngineService.getTypeEngineList(modelAuto.getId()));
        return "typeEngineList";
    }

    @GetMapping("/typeEngine/{markid}/{modelid}/{id}")
    public String getModification(@PathVariable("markid") MarkAuto markAuto,
                                @PathVariable("modelid") ModelAuto modelAuto,
                                @PathVariable("id") TypeEngine typeEngine,
                                Model model) {
        model.addAttribute("mark", markAuto);
        model.addAttribute("model", modelAuto);
        model.addAttribute("type", typeEngine);
        model.addAttribute("modificationList",
                modificationAutoService.getModificationListByTypeEngine(typeEngine.getId()));
        return "modificationList";
    }

    @GetMapping("/modification/{markid}/{modelid}/{typeEngineid}/{id}")
    public String getGroupRepair(@PathVariable("markid") MarkAuto markAuto,
                                @PathVariable("modelid") ModelAuto modelAuto,
                                @PathVariable("typeEngineid") TypeEngine typeEngine,
                                @PathVariable("id") ModificationAuto modificationAuto,
                                Model model) {
        model.addAttribute("mark", markAuto);
        model.addAttribute("model", modelAuto);
        model.addAttribute("type", typeEngine);
        model.addAttribute("modification",modificationAuto);
        model.addAttribute("groupRepairs",
                standardTimeService.getStandardTimeListByModification(modificationAuto)
                        .stream().map(StandardTime::getTypeRepairId).map(TypeRepair::getRepairGroup)
                        .collect(Collectors.toSet()));
        return "groupRepairList";
    }

    @GetMapping("/grouprepair/{markid}/{modelid}/{typeEngineid}/{modificationid}/{id}")
    public String getTypeRepair(@PathVariable("markid") MarkAuto markAuto,
                                @PathVariable("modelid") ModelAuto modelAuto,
                                @PathVariable("typeEngineid") TypeEngine typeEngine,
                                @PathVariable("modificationid") ModificationAuto modificationAuto,
                                @PathVariable("id") RepairGroup repairGroup,
                                Model model) {
        model.addAttribute("mark", markAuto);
        model.addAttribute("model", modelAuto);
        model.addAttribute("type", typeEngine);
        model.addAttribute("modification",modificationAuto);
        model.addAttribute("groupRepairs",
                standardTimeService.getStandardTimeListByModification(modificationAuto)
                        .stream().map(StandardTime::getTypeRepairId).map(TypeRepair::getRepairGroup)
                        .collect(Collectors.toSet()));
        model.addAttribute("typeRepairs",
                standardTimeService.getStandardTimeListByModificationAngRepairGroup(modificationAuto, repairGroup)
                        .stream().map(StandardTime::getTypeRepairId).collect(Collectors.toList()));
        model.addAttribute("standardTimes",
                standardTimeService.getStandardTimeListByModificationAngRepairGroup(modificationAuto,repairGroup)
                .stream().map(StandardTime::getStandardTime).collect(Collectors.toList()));
        return "typeRepairList";
    }
}
