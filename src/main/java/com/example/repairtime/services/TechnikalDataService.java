package com.example.repairtime.services;

import com.example.repairtime.cipher.RepairCodeDecrypt;
import com.example.repairtime.models.*;
import com.example.repairtime.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnikalDataService {

    private final SpecificationsCarRepository specificationsCarRepository;
    private final SpecificationGroupRepository specificationGroupRepository;
    private final SpecificationRowRepository specificationRowRepository;
    private final StandardTimeRepository standardTimeRepository;
    private final TypeRepairRepository typeRepairRepository;
    private final RepairGroupRepository repairGroupRepository;
    private final RepairGroupMainRepository repairGroupMainRepository;
    private final SpecificationsGroupNameRepository specificationsGroupNameRepository;

    public  void readeGroup(String fileName) throws FileNotFoundException {
        File dir = new File(fileName);
        List<SpecificationsGroupName> specificationsGroupNameList = new ArrayList<>();
        if (!specificationsGroupNameRepository.findAll().isEmpty()){
            specificationsGroupNameList = specificationsGroupNameRepository.findAll();
        }
        if (dir.isDirectory()) {
            for (File item : Objects.requireNonNull(dir.listFiles())) {
                if (item.isDirectory()) {
                    System.out.println(item.getName() + "  \t folder");
                } else {
//                    System.out.println(item.getName() + "  \t file");
                    Scanner sc = new Scanner(item, "UTF-8");
                    while (sc.hasNextLine()) {
                        String string = sc.nextLine();
//                        System.out.println(string);
                        if (specificationsGroupNameList.isEmpty() || specificationsGroupNameList.stream().noneMatch(s -> s.getName().equals(string))) {
                            SpecificationsGroupName specificationsGroupName = new SpecificationsGroupName();
                            specificationsGroupName.setName(string.trim());
                            specificationsGroupNameRepository.save(specificationsGroupName);
                        }
                    }
                }
            }
        }
    }


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
                    Scanner sc = new Scanner(item,"UTF-8");
                    int i = 1;

                    List<String> listResult = new ArrayList<>();
                    StringBuilder stringBuilder = new StringBuilder();
                    String repairCode = item.getName().replaceAll(".txt", "");
                    String repairCodeEncoded = Base64.getEncoder().encodeToString(repairCode.getBytes());
                    SpecificationsCar specificationsCar = new SpecificationsCar();
                    if (specificationsCarRepository.existsByRepairCode(repairCodeEncoded)){
//                         specificationsCar = specificationsCarRepository.getByRepairCode(repairCodeEncoded);
                        continue;
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
                    List<SpecificationsGroupName> specificationsGroupNameList = specificationsGroupNameRepository.findAll();
                    List<String> groupNameList = specificationsGroupNameList.stream().map(SpecificationsGroupName::getName).toList();

                    for (String s : listResult) {
//                        System.out.println(s);
                        if (s.equals("Головка блока цилиндров;*;*;") || s.equals("Другие моменты затяжки (двигатель);*;*;")){
                            s = s.replaceAll("\\*"," ");
                        }
                        if (s.contains(";*;*;")) {
                            s = s.replaceAll(";\\*;\\*;","");
                        }
                        String[] strings = s.split(";");

                        if (groupNameList.contains(s)) {
                            String finalS = s;
                            SpecificationsGroupName specificationsGroupName = specificationsGroupNameList.stream()
                                                                             .filter(sGName -> sGName.getName().equals(finalS))
                                                                             .findFirst().get();
                            SpecificationGroup specificationGroup = new SpecificationGroup();
                            if (specificationsCar.getSpecificationGroupList()==null) {
                                List<SpecificationGroup> specificationGroupList = new ArrayList<>();
                                specificationGroup.setSpecificationsGroupName(specificationsGroupName);
                                specificationGroupList.add(specificationGroup);
                                specificationsCar.setSpecificationGroupList(specificationGroupList);
                            } else if (!specificationsCar.getSpecificationGroupList().stream()
                                    .map(SpecificationGroup::getSpecificationsGroupName)
                                    .map(SpecificationsGroupName::getName).toList().contains(s)) {
                                specificationGroup.setSpecificationsGroupName(specificationsGroupName);
                                specificationsCar.getSpecificationGroupList().add(specificationGroup);
                            }
                            specificationGroup.setSpecificationsCar(specificationsCar);
//                            specificationGroupRepository.save(specificationGroup);
                        } else if (strings.length == 3) {
                            SpecificationRow specificationRow = new SpecificationRow();
                            specificationRow.setSpecificationName(strings[0].replaceAll("\\*", " ").trim());
                            specificationRow.setSpecificationUnit(strings[1].replaceAll("\\*", " ").trim());
                            specificationRow.setSpecificationValue(strings[2].replaceAll("\\*", " ").trim());

                            if (specificationsCar.getSpecificationGroupList()==null) {
                                List<SpecificationRow> specificationRowList = new ArrayList<>();
                                specificationRowList.add(specificationRow);
                                SpecificationGroup specificationGroup = new SpecificationGroup();
                                specificationGroup.setSpecificationRowList(specificationRowList);
                                List<SpecificationGroup> specificationGroupList = new ArrayList<>();
                                specificationGroupList.add(specificationGroup);
                                specificationsCar.setSpecificationGroupList(specificationGroupList);
                            } else {
                                List<SpecificationGroup> specificationGroupList = specificationsCar.getSpecificationGroupList();
                                SpecificationGroup specificationGroup1 = specificationsCar.getSpecificationGroupList().get(specificationGroupList.size() - 1);
                                if (specificationGroup1.getSpecificationRowList()==null) {
                                    List<SpecificationRow> specificationRowList = new ArrayList<>();
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
//                    List<SpecificationGroup> specificationGroupList = specificationGroupRepository
//                                                       .findAllBySpecificationsCar_RepairCode(repairCodeEncoded)
//                                                       .get();
//                    for (SpecificationGroup sg : specificationGroupList) {
//                        System.out.println(sg.getSpecificationsGroupName().getName());
//                        List<SpecificationRow> specificationRowList = sg.getSpecificationRowList();
//                        if (!(specificationRowList==null)) {
//                            for (SpecificationRow sr : specificationRowList) {
//                                System.out.println(sr.getSpecificationName() + " "
//                                        + sr.getSpecificationValue() + " "
//                                        + sr.getSpecificationUnit());
//                            }
//                        }
//                    }
                }
            }
        }
    }

    public  void readDirectories(String fileName) throws FileNotFoundException {

        File dir = new File(fileName);
        String PATTERN_1 = "[A-Z]{1}\\d{1,2}\\.\\d{4}\\.?\\d?";
        String PATTERN_2 = "\\d{1,2}\\.?\\d*";
        Pattern pattern1 = Pattern.compile(PATTERN_1);
        Pattern pattern2 = Pattern.compile(PATTERN_2);
        Matcher matcher;
        int counter = 1;
        if (dir.isDirectory()) {
            for (File item : Objects.requireNonNull(dir.listFiles())) {
                if (item.isDirectory()) {
                    System.out.println(item.getName() + "  \t folder");
                } else {
                    System.out.println(item.getName().replaceAll(".txt", ""));
                    Scanner sc = new Scanner(item);
                    StandardTime standardTime = new StandardTime();
                    List<StandardTime> standardTimeList = new ArrayList<>();
                    String repairCode = Base64.getEncoder().encodeToString(item.getName()
                            .replaceAll(".txt", "")
                            .getBytes(StandardCharsets.UTF_8));
                    if (standardTimeRepository.existsByRepairCode(repairCode)) {
                        standardTime = standardTimeRepository.getByRepairCode(repairCode);
                    } else {
                        standardTime.setRepairCode(repairCode);
                    }
                    List<TypeRepair> typeRepairList = typeRepairRepository.findAll();
                    List<String> vendorCodeList = typeRepairList.stream().map(TypeRepair::getVendorCode).toList();
                    while (sc.hasNext()) {
                        boolean typeIsPresent = false;
                        int indexTypeRepair = -1;
                        matcher = pattern1.matcher(sc.nextLine());
                        if (matcher.find()) {
                            String matcherString = matcher.group();
//                            System.out.println(matcher.group());
//                            Optional<TypeRepair> optionalTypeRepair = typeRepairRepository.getByVendorCode(matcher.group());
//                            if (optionalTypeRepair.isPresent()) {
                            if (vendorCodeList.contains(matcherString)) {
                                TypeRepair typeRepair = typeRepairList.stream()
                                        .filter(typeRepair1 -> typeRepair1.getVendorCode().equals(matcherString)).findFirst().get();
//                                TypeRepair typeRepair = optionalTypeRepair.get();
                                if (standardTime.getTypeRepairList().contains(typeRepair)) {
                                    indexTypeRepair = standardTime.getTypeRepairList().indexOf(typeRepair);
                                } else {
                                    standardTime.getTypeRepairList().add(typeRepair);
                                    typeIsPresent = true;
                                }
                            }
                            sc.nextLine();
//                            matcher = pattern2.matcher(sc.nextLine());
                            String str = sc.nextLine();
                            String resultString = (str.replaceAll("\\{\"N\"\\,", "")
                                    .replaceAll("\\}\\,",""));
//                            if (matcher.find()) {
//                            System.out.println(resultString);
                                if (indexTypeRepair != -1) {
                                    standardTime.getStandardTimes().set(indexTypeRepair, Double.parseDouble(resultString));
                                } else {
                                    standardTime.getStandardTimes().add(Double.parseDouble(resultString));
                                    if (typeIsPresent) {
//                                        standardTimeRepository.save(standardTime);
                                        standardTimeList.add(standardTime);
                                    }
                                    if (sc.hasNext()) sc.nextLine();
                                }
//                            }
                        }
                    }
                    sc.close();
                    standardTimeRepository.saveAll(standardTimeList);
                    //____________________________________________________
                    long point5 = System.currentTimeMillis();
                    System.out.println(counter++);
                    //_________________________________________
                }

            }
        }
    }

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
                if (matcher.find() &&
                        !matcher.group().equals("РЕМОНТНЫЕ РАБОТЫ НОРМО/ЧАС")) {
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
}
