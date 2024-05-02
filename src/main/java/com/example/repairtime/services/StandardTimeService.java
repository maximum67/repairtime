package com.example.repairtime.services;

import com.example.repairtime.models.*;
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
        String PATTERN_1 = "[A-Z]{1}\\d{1,2}\\.\\d{4}\\.?\\d?";
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
                            boolean typeIsPresent = false;
                            matcher = pattern1.matcher(sc.nextLine());
                            if (matcher.find()) {
//                            System.out.println(matcher.group());
                            if (typeRepairRepository.getByVendorCode(matcher.group()).isPresent()) {
                               standardTime.getTypeRepairList()
                                            .add(typeRepairRepository.getByVendorCode(matcher.group()).get());
                               typeIsPresent = true;
                                }
                                if (sc.hasNext()) sc.nextLine();
                            }
                            matcher = pattern2.matcher(sc.nextLine());
                            if (matcher.find()) {
//                            System.out.println(matcher.group());
                            standardTime.getStandardTimes().add(Double.parseDouble(matcher.group()));
                            if (typeIsPresent) {
                                standardTimeRepository.save(standardTime);
                            }
                                if (sc.hasNext()) sc.nextLine();
                            }
                        }
                }
            }
        }
    }

    public List<RepairGroupMain> getListOfRepairGroupMain(String typeRepairCode) {

   try {
     return  standardTimeRepository.getByRepairCode(typeRepairCode)
               .getTypeRepairList()
               .stream()
               .map(TypeRepair::getRepairGroup)
               .toList()
               .stream()
               .map(RepairGroup::getRepairGroupMain)
               .collect(Collectors.toSet()).stream().toList();
   }catch (NullPointerException e){
       return new LinkedList<>();
   }

    }

    public Optional<StandardTime> getStandardTimeByModification(ModificationAuto modificationAuto){
        return standardTimeRepository.getStandardTimeByRepairCode(modificationAuto.getRepairCode());
    }


    public List<Map<String, String>> getMapDataStandardTime(ModificationAuto modificationAuto, RepairGroup repairGroup){
        List<TypeRepair> typeRepairList = new LinkedList<>();
        List<Double> standardTimeList = new LinkedList<>();
        Optional<StandardTime> optionalStandardTime = getStandardTimeByModification(modificationAuto);
        if (optionalStandardTime.isPresent()) {
            StandardTime standardTime = optionalStandardTime.get();
//            System.out.println(standardTime.getId());
             typeRepairList = standardTime.getTypeRepairList();
             standardTimeList = standardTime.getStandardTimes();
        }
        return getMaps(repairGroup, typeRepairList, standardTimeList);
    }

    private static List<Map<String, String>> getMaps(RepairGroup repairGroup, List<TypeRepair> typeRepairList, List<Double> standardTimeList) {
        List<Map<String, String>> mapList = new LinkedList<>();
        Map<String, String> defaultMap = new HashMap<>();
        defaultMap.put("key", "Данные отсутствуют");
        for (int i = 0; i< typeRepairList.size(); i++) {
            Map<String, String> map = new HashMap<>();
            if (typeRepairList.get(i).getRepairGroup().getName().equals(repairGroup.getName())) {
                map.put("key", typeRepairList.get(i).getName());
                map.put("value", String.valueOf(standardTimeList.get(i)));
//                System.out.println(typeRepairList.get(i).getName()+" "+typeRepairList.get(i).getVendorCode()+" "+String.valueOf(standardTimeList.get(i)));
                mapList.add(map);
            }
        }
        if (mapList.isEmpty()) mapList.add(defaultMap);
        return mapList;
    }
}
