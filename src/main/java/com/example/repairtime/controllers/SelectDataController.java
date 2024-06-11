package com.example.repairtime.controllers;

import com.example.repairtime.models.*;
import com.example.repairtime.repositories.RepairGroupRepository;
import com.example.repairtime.repositories.TypeEngineRepository;
import com.example.repairtime.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/select")
public class SelectDataController {

    private final ModificationAutoService modificationAutoService;
    private final StandardTimeService standardTimeService;
    private final MarkAutoService markAutoService;
    private final ModelAutoService modelAutoService;
    private final TypeEngineService typeEngineService;
    private final SpecificationsCarService specificationsCarService;

    @GetMapping("/main")
    public String Main(Model model){
        model.addAttribute("page", 1);
        model.addAttribute("stringUrl", "mark");
        return "main";
    }

    @GetMapping("/{page}/mark")
    public String getMark(@PathVariable("page") Integer page, Model model){
        model.addAttribute("markList", markAutoService.getMarkAutoList());
        model.addAttribute("page", page);
        model.addAttribute("stringUrl", "mark");
        return "markList";
    }

    @GetMapping("/{page}/mark/{id}")
    public String getModel(@PathVariable("id") MarkAuto markAuto,
                           @PathVariable("page") Integer page, Model model){
        model.addAttribute("mark", markAuto);
        model.addAttribute("page", page);
        model.addAttribute("stringUrl", "mark/"+markAuto.getId());
        model.addAttribute("modelsList",
                modelAutoService.getModelsListByMark(markAuto.getId()));
        return "modelsList";
    }

    @GetMapping("/{page}/model/{markid}/{id}")
    public String getTypeEngine(@PathVariable("markid") MarkAuto markAuto,
                                @PathVariable("page") Integer page,
                                @PathVariable("id") ModelAuto modelAuto, Model model) {
        model.addAttribute("mark", markAuto);
        model.addAttribute("model", modelAuto);
        model.addAttribute("page", page);
        model.addAttribute("stringUrl", "model/"+markAuto.getId()+'/'+modelAuto.getId());
        model.addAttribute("typeEngineList",
                typeEngineService.getTypeEngineList(modelAuto.getId()));
        return "typeEngineList";
    }

    @GetMapping("/{page}/typeEngine/{markid}/{modelid}/{id}")
    public String getModification(@PathVariable("markid") MarkAuto markAuto,
                                @PathVariable("modelid") ModelAuto modelAuto,
                                @PathVariable("page") Integer page,
                                @PathVariable("id") TypeEngine typeEngine,
                                Model model) {
        model.addAttribute("mark", markAuto);
        model.addAttribute("model", modelAuto);
        model.addAttribute("type", typeEngine);
        model.addAttribute("page", page);
        model.addAttribute("stringUrl",
                "typeEngine/"+markAuto.getId()+'/'+modelAuto.getId()+'/'+typeEngine.getId());
        model.addAttribute("modificationList",
                modificationAutoService.getModificationListByTypeEngine(typeEngine.getId()));
        return "modificationList";
    }

    @GetMapping("/{page}/modification/{markid}/{modelid}/{typeEngineid}/{id}")
    public String getGroupRepair(@PathVariable("markid") MarkAuto markAuto,
                                @PathVariable("modelid") ModelAuto modelAuto,
                                @PathVariable("typeEngineid") TypeEngine typeEngine,
                                @PathVariable("id") ModificationAuto modificationAuto,
                                @PathVariable("page") Integer page,
                                Model model) throws NoSuchPaddingException,
                                IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        model.addAttribute("mark", markAuto);
        model.addAttribute("model", modelAuto);
        model.addAttribute("type", typeEngine);
        model.addAttribute("modification",modificationAuto);
        model.addAttribute("page", page);
        model.addAttribute("stringUrl",
                "modification/"+markAuto.getId()+'/'+modelAuto.getId()+'/'+typeEngine.getId()+'/'+modificationAuto.getId());
        model.addAttribute("groupRepairMains", standardTimeService.findAllRepairGroupMain());
        model.addAttribute("specificationGroupList",specificationsCarService.getSpecificationDataGroupListByModification(modificationAuto));
        return "repairTimeList";
    }

    @GetMapping("/modification/{markid}/{modelid}/{typeEngineid}/{id}/V1")
    public String getAutoCode(@PathVariable("markid") MarkAuto markAuto,
                                 @PathVariable("modelid") ModelAuto modelAuto,
                                 @PathVariable("typeEngineid") TypeEngine typeEngine,
                                 @PathVariable("id") ModificationAuto modificationAuto,
                                 Model model) {
        model.addAttribute("mark", markAuto);
        model.addAttribute("model", modelAuto);
        model.addAttribute("type", typeEngine);
        model.addAttribute("modification", modificationAuto);
        return "groupRepairList";
    }
}
