package com.example.repairtime.services;

import com.example.repairtime.models.ModificationAuto;
import com.example.repairtime.models.RepairGroup;
import com.example.repairtime.models.RepairGroupMain;
import com.example.repairtime.repositories.RepairGroupMainRepository;
import com.example.repairtime.repositories.RepairGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RepairGroupService {

    private final RepairGroupRepository repairGroupRepository;
    private final RepairGroupMainRepository repairGroupMainRepository;


    public void readFileGroup(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner sc = new Scanner(file);

        String PATTERN = "(\\(?((ABS)|(ESP)|(T)|[А-Я])+\\d*\\.?\\)?\\,*\\/?\\s*)+";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher;
        while (sc.hasNext()) {
            matcher = pattern.matcher(sc.nextLine().replaceAll("Услуга", "услуга"));
            if (matcher.find()) {
//                System.out.println(matcher.group());  // Выводит: подстрокой
                RepairGroup repairGroup = new RepairGroup();
                if (repairGroupRepository.existsByName(matcher.group())) {
                    repairGroup = repairGroupRepository.getByName(matcher.group()).get();
                } else {
                    repairGroup.setName(matcher.group());
                }
                matcher = pattern.matcher(sc.nextLine().replaceAll("Услуга", "услуга"));
                if (matcher.find()) {
//                    System.out.println(matcher.group());  // Выводит: подстрокой
                    RepairGroupMain repairGroupMain = new RepairGroupMain();
                    if (repairGroupMainRepository.existsByName(matcher.group())) {
                        repairGroupMain = repairGroupMainRepository.getByName(matcher.group()).get();
                    } else {
                        repairGroupMain.setName(matcher.group());
                    }
                    repairGroup.setRepairGroupMain(repairGroupMain);
                    repairGroupRepository.save(repairGroup);
                }
            }
        }
    }

    public List<RepairGroupMain> findAllRepairGroupMain() {
        return repairGroupMainRepository.findAll();
    }

    public List<Map<String, String>> getAllRepairGroupOfModification(RepairGroupMain repairGroupMain, ModificationAuto modificationAuto) {
        List<RepairGroup> repairGroupList = repairGroupMainRepository.getByName(repairGroupMain.getName()).get().getRepairGroupList();
        List<Map<String, String>> mapList = new LinkedList<>();
        for (RepairGroup rg : repairGroupList) {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("key", rg.getName());
            map.put("value", String.valueOf(rg.getId()));
            mapList.add(map);
        }
        return mapList;
    }
}