package com.example.repairtime.services;

import com.example.repairtime.models.MarkAuto;
import com.example.repairtime.models.ModelAuto;
import com.example.repairtime.models.ModificationAuto;
import com.example.repairtime.models.TypeEngine;
import com.example.repairtime.repositories.MarkAutoRepository;
import com.example.repairtime.repositories.ModificationAutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RepairDataService {

    private final ModificationAutoRepository modificationAutoRepository;
    private final MarkAutoRepository markAutoRepository;

    public List<ModificationAuto> modificationAutoList(){
        return modificationAutoRepository.findAll();
    }

    public ModificationAuto getModificationAutoByName(Long id){
        return modificationAutoRepository.getReferenceById(id);
    }

    public void saveModification(ModificationAuto modificationAuto){
        modificationAutoRepository.save(modificationAuto);
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

        ModificationAuto modificationAuto = new ModificationAuto();
        modificationAuto.setNameModificationAuto(listModification.get(0));

        TypeEngine typeEngine = new TypeEngine();
        typeEngine.setNameTypeEngine(listEngineType.get(0));
//        typeEngine.setModificationAutoList(Collections.singletonList(modificationAuto));

        ModelAuto modelAuto = new ModelAuto();
        modelAuto.setNameModel(listModel.get(0));
//        modelAuto.setTypeEngineList(Collections.singletonList(typeEngine));


        MarkAuto markAuto = new MarkAuto();
        markAuto.setNameMark(listMark.get(0));
//        markAuto.setModelAutoList(Collections.singletonList(modelAuto));

        markAutoRepository.save(markAuto);
    }
}
