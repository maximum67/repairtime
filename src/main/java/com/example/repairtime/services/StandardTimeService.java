package com.example.repairtime.services;

import com.example.repairtime.models.ModificationAuto;
import com.example.repairtime.models.RepairGroup;
import com.example.repairtime.models.StandardTime;
import com.example.repairtime.models.TypeRepair;
import com.example.repairtime.repositories.StandardTimeRepository;
import com.example.repairtime.repositories.TypeRepairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StandardTimeService {

    private final StandardTimeRepository standardTimeRepository;
    private final TypeRepairRepository typeRepairRepository;


    public  void readDirectories(String fileName) throws FileNotFoundException {

        File dir = new File(fileName);
        String PATTERN_1 = "[A-Z]{1}\\d{1,2}\\.\\d{4}";
        String PATTERN_2 = "\\d{1,2}\\.\\d{1,2}";
        Pattern pattern1 = Pattern.compile(PATTERN_1);
        Pattern pattern2 = Pattern.compile(PATTERN_2);
        Matcher matcher;
        if (dir.isDirectory()) {
            for (File item : Objects.requireNonNull(dir.listFiles())) {
                if (item.isDirectory()) {
                    System.out.println(item.getName() + "  \t folder");
                } else {

                    System.out.println(item.getName().replaceAll(".txt", ""));
                    Scanner sc = new Scanner(item);
                    StandardTime standardTime = new StandardTime();
                    String repairCode =item.getName().replaceAll(".txt", "");
                    if (standardTimeRepository.existsByRepairCode(repairCode)) {
                        standardTime = standardTimeRepository.getByRepairCode(repairCode);
                    }else{
                        standardTime.setRepairCode(repairCode);
                    }
                        while (sc.hasNext()) {
                            matcher = pattern1.matcher(sc.nextLine());
                            if (matcher.find()) {
                            System.out.println(matcher.group());
                            standardTime.getTypeRepairList()
                                        .add(typeRepairRepository.getByVendorCode(matcher.group()).get());
                                if (sc.hasNext()) sc.nextLine();
                            }
                            matcher = pattern2.matcher(sc.nextLine());
                            if (matcher.find()) {
                            System.out.println(matcher.group());
                            standardTime.getStandardTimes().add(Double.parseDouble(matcher.group()));
                                if (sc.hasNext()) sc.nextLine();
                            }
                        }

                }
            }
        }
    }
}
