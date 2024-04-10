package com.example.repairtime.services;

import com.example.repairtime.models.MarkAuto;
import com.example.repairtime.models.ModelAuto;
import com.example.repairtime.models.ModificationAuto;
import com.example.repairtime.models.TypeEngine;
import com.example.repairtime.repositories.MarkAutoRepository;
import com.example.repairtime.repositories.ModelAutoRepository;
import com.example.repairtime.repositories.ModificationAutoRepository;
import com.example.repairtime.repositories.TypeEngineRepository;
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

    public List<ModificationAuto> modificationAutoList(){
        return modificationAutoRepository.findAll();
    }


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
        List<String> listStandardHours = new LinkedList<>();
        map.forEach((key, value) -> resultList.add(value.get(1)));
        map.forEach((key,value)-> listGroup.add(value.get(2)));
        map.forEach((key,value)-> listTypeRepair.add(value.get(3)));
        map.forEach((key,value)-> listStandardHours.add(value.get(4)));

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
        for (int i = 0; i < 25; i++) {

            String markName = listMark.get(i);
            System.out.println(markName);
            String modelName = listModel.get(i);
            System.out.println(modelName);
            String typeEngineName = listEngineType.get(i);
            System.out.println(typeEngineName);
            String modificationName = listModification.get(i);
            System.out.println(modificationName);
            MarkAuto markAuto = new MarkAuto();
            ModelAuto modelAuto = new ModelAuto();
            TypeEngine typeEngine = new TypeEngine();
            ModificationAuto modificationAuto = new ModificationAuto();
            if (markAutoRepository.findByName(markName).isPresent()) {
                markAuto = markAutoRepository.findByName(markName).get();
//                System.out.println(markAuto.getName());
                if (modelAutoRepository.findByName(modelName).isPresent()) {
                    modelAuto = modelAutoRepository.findByName(modelName).get();
//                    System.out.println(modelAuto.getName());
                    if (typeEngineRepository.findByName(typeEngineName).isPresent()) {
                        typeEngine = typeEngineRepository.findByName(typeEngineName).get();
//                        System.out.println(typeEngine.getName());
                        if (modificationAutoRepository.findByName(modificationName).isPresent()) {
                            modificationAuto = modificationAutoRepository.findByName(modificationName).get();
//                            System.out.println(modificationAuto.getName());
                        } else {
                            modificationAuto.setName(modificationName);
                            modificationAuto.setTypeEngine(typeEngine);
                            typeEngine.getModificationAutoList().add(modificationAuto);
//                            modificationAutoRepository.save(modificationAuto);
                        }
                    } else {
                        typeEngine.setName(typeEngineName);
                        typeEngine.setModelAuto(modelAuto);
                        modelAuto.getTypeEngineList().add(typeEngine);
                        modificationAuto.setName(modificationName);
                        modificationAuto.setTypeEngine(typeEngine);
                        typeEngine.setModificationAutoList(Collections.singletonList(modificationAuto));
//                        typeEngineRepository.save(typeEngine);
                    }
                } else {

                    modelAuto.setName(modelName);
                    modelAuto.setMarkAuto(markAuto);
                    markAuto.getModelAutoList().add(modelAuto);
                    typeEngine.setName(typeEngineName);
                    typeEngine.setModelAuto(modelAuto);
                    modificationAuto.setName(modificationName);
                    modificationAuto.setTypeEngine(typeEngine);
                    modelAuto.setTypeEngineList(Collections.singletonList(typeEngine));
                    typeEngine.setModificationAutoList(Collections.singletonList(modificationAuto));
//                    modelAutoRepository.save(modelAuto);
                }
            } else {
                markAuto.setName(markName);
                modelAuto.setName(modelName);
                modelAuto.setMarkAuto(markAuto);
                typeEngine.setName(typeEngineName);
                typeEngine.setModelAuto(modelAuto);
                modificationAuto.setName(modificationName);
                modificationAuto.setTypeEngine(typeEngine);
                markAuto.setModelAutoList(Collections.singletonList(modelAuto));
                modelAuto.setTypeEngineList(Collections.singletonList(typeEngine));
                typeEngine.setModificationAutoList(Collections.singletonList(modificationAuto));
//                markAutoRepository.save(markAuto);
            }
            markAutoRepository.save(markAuto);
        }

    }
}
