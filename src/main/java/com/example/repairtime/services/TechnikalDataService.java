package com.example.repairtime.services;

import com.example.repairtime.models.SpecificationGroup;
import com.example.repairtime.models.SpecificationRow;
import com.example.repairtime.models.SpecificationsCar;
import com.example.repairtime.repositories.SpecificationGroupRepository;
import com.example.repairtime.repositories.SpecificationRowRepository;
import com.example.repairtime.repositories.SpecificationsCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TechnikalDataService {

    private final SpecificationsCarRepository specificationsCarRepository;
    private final SpecificationGroupRepository specificationGroupRepository;
    private final SpecificationRowRepository specificationRowRepository;

    public void readTechnikalData(String fileName) throws FileNotFoundException {

        File dir = new File(fileName);
        String PATTERN_1 = "\\{\"[S]{1}\",\"";
        String PATTERN_2 = "\"},\\d*}*,*";
        Pattern pattern1 = Pattern.compile(PATTERN_1);
        Pattern pattern2 = Pattern.compile(PATTERN_2);
        Matcher matcher;
        if (dir.isDirectory()) {
            for (File item : Objects.requireNonNull(dir.listFiles())) {
                if (item.isDirectory()) {
                    System.out.println(item.getName() + "  \t folder");
                } else {
//                    System.out.println(item.getName().replaceAll(".txt", ""));
                    Scanner sc = new Scanner(item);
                    int i = 1;
                    List<String> listResult = new LinkedList<>();
                    StringBuilder stringBuilder = new StringBuilder();
                    String repairCode = item.getName().replaceAll(".txt", "");
                    String repairCodeEncoded = Base64.getEncoder().encodeToString(repairCode.getBytes());
                    SpecificationsCar specificationsCar = new SpecificationsCar();
                    if (specificationsCarRepository.existsByRepairCode(repairCodeEncoded)){
                         specificationsCar = specificationsCarRepository.getByRepairCode(repairCodeEncoded);
                    }else {
                        specificationsCar.setRepairCode(repairCodeEncoded);
                        specificationsCarRepository.save(specificationsCar);
                    }
                    while (sc.hasNext()) {
                        String string1 = sc.nextLine();
                        String string2 = ";";
                        matcher = pattern1.matcher(string1);
                        if (matcher.find()) {
//                            System.out.println(string1);
                            String string = string1.replaceAll(String.valueOf(pattern1), "")
                                    .replaceAll(String.valueOf(pattern2), "") + ";";
                            if (string.equals(string2)) {
//                                System.out.print("$");
                                stringBuilder.append("*;");
                            } else {
//                                System.out.print(string);
                                stringBuilder.append(string);
                            }
                            if (i % 3 == 0) {
//                                System.out.println();
                                stringBuilder.append("\n");
                            }
                            i++;
                        }
//                        matcher = pattern2.matcher(sc.nextLine());
//                        if (matcher.find()) {
////                            System.out.println(matcher.group());
//                            if(sc.hasNext()) sc.nextLine();
//                        }
                    }
                    listResult = Arrays.asList(stringBuilder.toString().split("\n"));


                    for (String s : listResult) {
//                        System.out.println(s);
                        if (s.contains(";*;*;")) {
                            SpecificationGroup specificationGroup = new SpecificationGroup();
                            specificationGroup.setHeaderGroup(s.replaceAll(";\\*;\\*;", ""));
//                            System.out.println(s);
                            if (specificationsCar.getSpecificationGroupList()==null) {
                                List<SpecificationGroup> specificationGroupList = new LinkedList<>();
                                specificationGroupList.add(specificationGroup);
                                specificationsCar.setSpecificationGroupList(specificationGroupList);
                            } else {
                                specificationsCar.getSpecificationGroupList().add(specificationGroup);
                            }
                            specificationGroup.setSpecificationsCar(specificationsCar);
//                            specificationGroupRepository.save(specificationGroup);
                        } else {
                            String[] strings = s.split(";");
//                            Arrays.stream(strings).forEach(System.out::println);
                            SpecificationRow specificationRow = new SpecificationRow();
                            specificationRow.setSpecificationName(strings[0]);
                            specificationRow.setSpecificationUnit(strings[1].replaceAll("\\*",""));
                            specificationRow.setSpecificationValue(strings[2].replaceAll("\\*",""));

                            if (specificationsCar.getSpecificationGroupList()==null) {
                                List<SpecificationRow> specificationRowList = new LinkedList<>();
                                specificationRowList.add(specificationRow);
                                SpecificationGroup specificationGroup = new SpecificationGroup();
                                specificationGroup.setSpecificationRowList(specificationRowList);
                                List<SpecificationGroup> specificationGroupList = new LinkedList<>();
                                specificationGroupList.add(specificationGroup);
                                specificationsCar.setSpecificationGroupList(specificationGroupList);
                            } else {
                                List<SpecificationGroup> specificationGroupList = specificationsCar.getSpecificationGroupList();
                                SpecificationGroup specificationGroup1 = specificationsCar.getSpecificationGroupList().get(specificationGroupList.size() - 1);
                                if (specificationGroup1.getSpecificationRowList()==null) {
                                    List<SpecificationRow> specificationRowList = new LinkedList<>();
                                    specificationRowList.add(specificationRow);
                                    specificationsCar.getSpecificationGroupList().get(specificationGroupList.size() - 1).setSpecificationRowList(specificationRowList);
                                } else {
//                                    System.out.println(specificationsCar.getSpecificationGroupList().get(specificationGroupList.size() - 1).getHeaderGroup());
                                    specificationsCar.getSpecificationGroupList().get(specificationGroupList.size() - 1).getSpecificationRowList().add(specificationRow);
//                                    SpecificationRow sr1 = specificationsCar.getSpecificationGroupList()
//                                            .get(specificationGroupList.size() - 1)
//                                            .getSpecificationRowList().get(specificationsCar.getSpecificationGroupList()
//                                                    .get(specificationGroupList.size() - 1).getSpecificationRowList().size()-1);
//                                    System.out.println(sr1.getSpecificationName() + " "
//                                            + sr1.getSpecificationUnit() + " "
//                                            + sr1.getSpecificationValue());
                                }
                                specificationRow.setSpecificationGroup(specificationGroup1);
                            }
//                            specificationRowRepository.save(specificationRow);
                        }
                    }
                    specificationsCarRepository.save(specificationsCar);
                    System.out.println(repairCode);
//                    System.out.println(specificationsCarRepository.getByRepairCode(repairCode));
                    List<SpecificationGroup> specificationGroupList = specificationGroupRepository
                                                       .findAllBySpecificationsCar_RepairCode(repairCodeEncoded)
                                                       .get();
                    for (SpecificationGroup sg : specificationGroupList) {
//                        System.out.println(sg.getHeaderGroup());
                        List<SpecificationRow> specificationRowList = sg.getSpecificationRowList();
                        if (!(specificationRowList==null)) {
                            for (SpecificationRow sr : specificationRowList) {
//                                System.out.println(sr.getSpecificationName() + " "
//                                        + sr.getSpecificationValue() + " "
//                                        + sr.getSpecificationUnit());
                            }
                        }
                    }
                }
            }
        }
    }
}
