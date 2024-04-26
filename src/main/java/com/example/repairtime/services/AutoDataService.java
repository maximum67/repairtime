package com.example.repairtime.services;


import com.example.repairtime.models.*;
import com.example.repairtime.repositories.MarkAutoRepository;
import com.example.repairtime.repositories.ModelAutoRepository;
import com.example.repairtime.repositories.ModificationAutoRepository;
import com.example.repairtime.repositories.TypeEngineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AutoDataService {

    private final MarkAutoRepository markAutoRepository;
    private final ModelAutoRepository modelAutoRepository;
    private final TypeEngineRepository typeEngineRepository;
    private final ModificationAutoRepository modificationAutoRepository;

    public void readFileAndSaveData(String filename) throws IOException {

        List<String> listMark = new LinkedList<>();
        List<String> listModel = new LinkedList<>();
        List<String> listEngineType = new LinkedList<>();
        List<String> listModification = new LinkedList<>();


        ParsingFile parsingFile = new ParsingFile();
        Map<Integer, List<String>> map = parsingFile.read(filename);
        List<String> resultList = new LinkedList<>();
        List<String> repairCodes = new LinkedList<>();

        map.forEach((key, value) -> resultList.add(value.get(0)));
        map.forEach((key, value) -> repairCodes.add(value.get(1)));


        for (String string : resultList) {
            string = string.replaceAll(";", "*");
            System.out.println(string);
            string = string.replaceAll("\\d{4}\\sоб/мин\\s", "");
//            System.out.println(string);
            List<String> list = new LinkedList<>(Arrays.asList(string.split("\\*")));
            listMark.add(list.get(0));
            listModel.add(list.get(1));
            listEngineType.add(list.get(2));
            listModification.add(list.get(3));
        }

        for (int i = 0; i < resultList.size(); i++) {

            String markName = listMark.get(i);
//            System.out.println(markName);
            String modelName = listModel.get(i);
//            System.out.println(modelName);
            String typeEngineName = listEngineType.get(i);
//            System.out.println(typeEngineName);
            String modificationName = listModification.get(i);
//            System.out.println(modificationName);

            MarkAuto markAuto = new MarkAuto();
            ModelAuto modelAuto = new ModelAuto();
            TypeEngine typeEngine = new TypeEngine();
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
                            modificationAuto.setRepairCode(repairCodes.get(i));
//                            typeEngine.getModificationAutoList().add(modificationAuto);
                            modificationAutoRepository.save(modificationAuto);
                        }
                    } else {

                        modificationAuto.setName(modificationName);
                        modificationAuto.setTypeEngine(typeEngine);
                        modificationAuto.setRepairCode(repairCodes.get(i));
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
                    modificationAuto.setRepairCode(repairCodes.get(i));
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
                modificationAuto.setRepairCode(repairCodes.get(i));
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
        }

    }
}
