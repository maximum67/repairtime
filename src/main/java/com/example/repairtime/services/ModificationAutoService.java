package com.example.repairtime.services;

import com.example.repairtime.models.ModificationAuto;
import com.example.repairtime.repositories.ModificationAutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ModificationAutoService {

    private final ModificationAutoRepository modificationAutoRepository;

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
        ParsingFile parsingFile = new ParsingFile();
        Map<Integer, List<String>> map = parsingFile.read(filename);
        List<String> resultList = new LinkedList<>();
        map.forEach((key, value) -> resultList.add(value.get(1)));
        ModificationAuto modificationAuto = Parser.parsingString(resultList);
        modificationAutoRepository.save(modificationAuto);
    }
}
