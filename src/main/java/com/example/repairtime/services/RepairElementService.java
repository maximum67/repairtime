package com.example.repairtime.services;

import com.example.repairtime.models.RepairGroup;
import com.example.repairtime.models.TypeRepair;
import com.example.repairtime.repositories.RepairGroupRepository;
import com.example.repairtime.repositories.TypeEngineRepository;
import com.example.repairtime.repositories.TypeRepairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RepairElementService {

//    private final MarkAutoRepository markAutoRepository;
//    private final ModelAutoRepository modelAutoRepository;
//    private final TypeEngineRepository typeEngineRepository;
//    private final ModificationAutoRepository modificationAutoRepository;
    private final RepairGroupRepository repairGroupRepository;
    private final TypeRepairRepository typeRepairRepository;
//    private final StandardTimeRepository standardTimeRepository;


    public void readFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner sc = new Scanner(file);

        String PATTERN_1 = "([А-Я]?[а-я*\\(?а-я*\\)?\\-?а-я*]*\\,*\\s)+([A-z])*([А-Я]?[а-я*\\(?а-я*\\)?\\-?а-я*]*\\,*\\s*)*";
        String PATTERN_2 = "([А-Я]{2,}\\,*\\s*)+";
        String PATTERN_3 = "[A-Z]{1}\\d{1,2}\\.\\d{4}";
        int i = 0;
        Pattern pattern1 = Pattern.compile(PATTERN_1);
        Pattern pattern2 = Pattern.compile(PATTERN_2);
        Pattern pattern3 = Pattern.compile(PATTERN_3);
        Matcher matcher;
        while (i<6084) {
            TypeRepair typeRepair = new TypeRepair();
            matcher = pattern1.matcher(sc.nextLine());
            if (matcher.find()) {
                if (typeRepairRepository.existsByName(matcher.group())){
                   typeRepair = typeRepairRepository.getByName(matcher.group()).get();
                }else{
                    typeRepair.setName(matcher.group());
                }
                System.out.println(matcher.group());  // Выводит: подстрокой
            }
            matcher = pattern2.matcher(sc.nextLine());

            if (matcher.find()){
                if (repairGroupRepository.existsByName(matcher.group())) {
                    typeRepair.setRepairGroup(repairGroupRepository.getByName(matcher.group()).get());
                }else{
                        RepairGroup repairGroup = new RepairGroup();
                        repairGroup.setName(matcher.group());
                        typeRepair.setRepairGroup(repairGroup);
                    }
               System.out.println(matcher.group());  // Выводит: подстрокой
            }
            matcher = pattern3.matcher(sc.nextLine());
            if (matcher.find()) {
                typeRepair.setVendorCode(matcher.group());
                System.out.println(matcher.group());  // Выводит: подстрокой
                typeRepairRepository.save(typeRepair);
            }
            i++;
        }
    }
}
