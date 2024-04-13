package com.example.repairtime.services;

import com.example.repairtime.models.*;
import com.example.repairtime.repositories.*;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RepairDataService {

    private final MarkAutoRepository markAutoRepository;
    private final ModelAutoRepository modelAutoRepository;
    private final TypeEngineRepository typeEngineRepository;
    private final ModificationAutoRepository modificationAutoRepository;
    private final RepairGroupRepository repairGroupRepository;
    private final TypeRepairRepository typeRepairRepository;
    private final StandardTimeRepository standardTimeRepository;


    public void writingFileAndSave(String filename) throws IOException {

        List<String> listMark = new LinkedList<>();
        List<String> listModel = new LinkedList<>();
        List<String> listEngineType = new LinkedList<>();
        List<String> listModification = new LinkedList<>();

        ParsingFile parsingFile = new ParsingFile();
        Map<Integer, List<String>> map = parsingFile.read(filename);
        List<String> resultList = new LinkedList<>();
        List<String> listGroup = new LinkedList<>();
        List<String> listTypeRepair = new LinkedList<>();
        List<Double> listStandardHours = new LinkedList<>();
        map.forEach((key, value) -> resultList.add(value.get(1)));
        map.forEach((key,value)-> listGroup.add(value.get(2)));
        map.forEach((key,value)-> listTypeRepair.add(value.get(3)));
        map.forEach((key,value)-> listStandardHours.add(Double.valueOf(value.get(4))));

        for (String string : resultList) {
            string = string.replaceAll(" / ", "*");
            string = string.replaceAll("\\d{4}\\sоб/мин\\s","");
            List<String> list = new LinkedList<>(Arrays.asList(string.split("\\*")));
            listMark.add(list.get(0));
            listModel.add(list.get(1));
            listEngineType.add(list.get(2));
            listModification.add(list.get(3));
        }


//        modificationAuto.setNameModificationAuto(listModification.get(0));


//        typeEngine.setNameTypeEngine(listEngineType.get(0));
//        typeEngine.setModificationAutoList(Collections.singletonList(modificationAuto));

//        markAuto.setModelAutoList(Collections.singletonList(modelAuto));

//        modelAuto.setNameModel(listModel.get(0));
//        modelAuto.setTypeEngineList(Collections.singletonList(typeEngine));
        for (int i = 0; i <resultList.size(); i++) {

            String markName = listMark.get(i);
//            System.out.println(markName);
            String modelName = listModel.get(i);
//            System.out.println(modelName);
            String typeEngineName = listEngineType.get(i);
//            System.out.println(typeEngineName);
            String modificationName = listModification.get(i);
//            System.out.println(modificationName);
            String repairGroupName = listGroup.get(i);
//            System.out.println(repairGroupName);
            String typeRepairName = listTypeRepair.get(i);
//            System.out.println(typeRepairName);
            Double standardTimeName = listStandardHours.get(i);
//            System.out.println(standardTimeName);

            MarkAuto markAuto = new MarkAuto();
            ModelAuto modelAuto = new ModelAuto();
            TypeEngine typeEngine = new TypeEngine();
            RepairGroup repairGroup = new RepairGroup();
            TypeRepair typeRepair = new TypeRepair();
            StandardTime standardTime = new StandardTime();
            StandardTimeKey standardTimeKey = new StandardTimeKey();

            ModificationAuto modificationAuto = new ModificationAuto();
            if (markAutoRepository.findByName(markName).isPresent()) {
                markAuto = markAutoRepository.findByName(markName).get();
//                System.out.println(markAuto.getName());
                if (modelAutoRepository.findByName(modelName).isPresent()) {
                    modelAuto = modelAutoRepository.findByName(modelName).get();
                    final ModelAuto modelAutoTemp = modelAuto;
//                    System.out.println(modelAuto.getName());
                    if (typeEngineRepository.findAllByName(typeEngineName).isPresent() &&
                            !typeEngineRepository.findAllByName(typeEngineName).get()
                                    .stream().filter(typeEngine1 -> typeEngine1.getModelAuto().equals(modelAutoTemp))
                                    .toList().isEmpty()) {
                        typeEngine = typeEngineRepository.findAllByName(typeEngineName).get()
                                .stream().filter(typeEngine1 -> typeEngine1.getModelAuto().equals(modelAutoTemp))
                                .findFirst().get();
//                        System.out.println(typeEngine.getName());
                        if (modificationAutoRepository.findByName(modificationName).isPresent()) {
                            modificationAuto = modificationAutoRepository.findByName(modificationName).get();
//                            System.out.println(modificationAuto.getName());
                        } else {
                            modificationAuto.setName(modificationName);
                            modificationAuto.setTypeEngine(typeEngine);
//                            typeEngine.getModificationAutoList().add(modificationAuto);
                            modificationAutoRepository.save(modificationAuto);
                        }
                    } else {

                        modificationAuto.setName(modificationName);
                        modificationAuto.setTypeEngine(typeEngine);

                        List<ModificationAuto> modifList = new LinkedList<>();
                        modifList.add(modificationAuto);
                        typeEngine.setModificationAutoList(modifList);
                        typeEngine.setName(typeEngineName);
                        typeEngine.setModelAuto(modelAuto);

//                        modelAuto.getTypeEngineList().add(typeEngine);
                        typeEngineRepository.save(typeEngine);
                    }
                } else {
                    modelAuto.setName(modelName);
                    modelAuto.setMarkAuto(markAuto);
                    typeEngine.setName(typeEngineName);
                    typeEngine.setModelAuto(modelAuto);
                    modificationAuto.setName(modificationName);
                    modificationAuto.setTypeEngine(typeEngine);

                    List<TypeEngine> engineList = new LinkedList<>();
                    engineList.add(typeEngine);
                    modelAuto.setTypeEngineList(engineList);

                    List<ModificationAuto> modifList = new LinkedList<>();
                    modifList.add(modificationAuto);
                    typeEngine.setModificationAutoList(modifList);
                    modelAutoRepository.save(modelAuto);
//                    ModelAuto model = modelAutoRepository.findByName(modelName).get();
//                    markAuto.getModelAutoList().add(model);



                }
            } else {
                markAuto.setName(markName);
                modelAuto.setName(modelName);
                modelAuto.setMarkAuto(markAuto);
                typeEngine.setName(typeEngineName);
                typeEngine.setModelAuto(modelAuto);
                modificationAuto.setName(modificationName);
                modificationAuto.setTypeEngine(typeEngine);

                List<ModelAuto> modelList = new LinkedList<>();
                modelList.add(modelAuto);
                markAuto.setModelAutoList(modelList);

                List<TypeEngine> engineList = new LinkedList<>();
                engineList.add(typeEngine);
                modelAuto.setTypeEngineList(engineList);

                List<ModificationAuto> modifList = new LinkedList<>();
                modifList.add(modificationAuto);
                typeEngine.setModificationAutoList(modifList);

                markAutoRepository.save(markAuto);
            }

            repairGroup.setName(repairGroupName);
            if (!repairGroupRepository.existsByName(repairGroupName)){
                repairGroupRepository.save(repairGroup);
            }
            repairGroup = repairGroupRepository.getByName(repairGroupName).get();


            typeRepair.setName(typeRepairName);
            typeRepair.setRepairGroup(repairGroup);
            if (!typeRepairRepository.existsByName(typeRepairName)){
                typeRepairRepository.save(typeRepair);
            }
            typeRepair = typeRepairRepository.getByName(typeRepairName).get();


            standardTimeKey.setTypeRepairId(typeRepair);
            standardTimeKey.setModificationAutoId(modificationAuto);
            if (!standardTimeRepository.existsExampleStandardTime(standardTimeKey)) {
                standardTime.setStandardTime(standardTimeName);
                standardTime.setTypeRepairId(typeRepair);
                standardTime.setModificationAutoId(modificationAuto);
                standardTimeRepository.save(standardTime);


//                System.out.println("type " + typeRepair.getId() + "  modif " + modificationAuto.getId() + "  group " + repairGroup.getName());
            }
        }

    }
}
