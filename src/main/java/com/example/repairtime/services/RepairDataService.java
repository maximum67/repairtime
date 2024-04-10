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

        MarkAuto markAuto = new MarkAuto();
        ModelAuto modelAuto = new ModelAuto();
        TypeEngine typeEngine = new TypeEngine();
        ModificationAuto modificationAuto = new ModificationAuto();
        if (markAutoRepository.findByName(listMark.get(0)).isPresent()) {
           markAuto = markAutoRepository.findByName(listMark.get(0)).get();
            if (markAuto.getModelAutoList().stream()
                    .findFirst()
                    .filter(modelAuto1 -> modelAuto1.getName()
                    .equals(listModel.get(0))).isPresent()) {
                modelAuto = modelAutoRepository.findByName(listModel.get(0));
                if (modelAuto.getTypeEngineList().stream()
                        .findFirst()
                        .filter(typeEngine1 -> typeEngine1.getName()
                        .equals(listEngineType.get(0))).isPresent()){
                    typeEngine = typeEngineRepository.findByName(listEngineType.get(0));
                    if (typeEngine.getModificationAutoList().stream()
                            .findFirst()
                            .filter(modificationAuto1 -> modificationAuto1.getName()
                            .equals(listModification.get(0))).isPresent()){
                       modificationAuto = modificationAutoRepository.findByName(listModification.get(0));
                    }else{
                       modificationAuto.setName(listModification.get(0));
                    }
                }else{
                    typeEngine.setName(listEngineType.get(0));
                    typeEngine.setModificationAutoList(Collections.singletonList(modificationAuto));
                }
            }else{
                modelAuto.setName(listModel.get(0));
                modelAuto.setTypeEngineList(Collections.singletonList(typeEngine));
            }
        }else{
            markAuto.setName(listMark.get(0));
            markAuto.setModelAutoList(Collections.singletonList(modelAuto));

        }


        markAutoRepository.save(markAuto);
    }
}
