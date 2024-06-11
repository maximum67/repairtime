package com.example.repairtime.services;

import com.example.repairtime.cipher.RepairCodeDecrypt;
import com.example.repairtime.models.*;
import com.example.repairtime.repositories.RepairGroupMainRepository;
import com.example.repairtime.repositories.RepairGroupRepository;
import com.example.repairtime.repositories.StandardTimeRepository;
import com.example.repairtime.repositories.TypeRepairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StandardTimeService {

    private final StandardTimeRepository standardTimeRepository;
    private final TypeRepairRepository typeRepairRepository;
    private final RepairGroupRepository repairGroupRepository;
    private final RepairGroupMainRepository repairGroupMainRepository;


    public List<RepairGroupMain> findAllRepairGroupMain() {
        return repairGroupMainRepository.findAll();
    }

    public List<Map<String, String>> getAllRepairGroupOfModification(RepairGroupMain repairGroupMain, ModificationAuto modificationAuto) throws NoSuchPaddingException,
                                                                       IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Optional<StandardTime> optionalStandardTime = getStandardTimeByModification(modificationAuto);
        List<RepairGroup> repairGroups = new ArrayList<>();
        if (optionalStandardTime.isPresent()) {
           StandardTime standardTime = optionalStandardTime.get();
           repairGroups = standardTime.getTypeRepairList().stream().map(TypeRepair::getRepairGroup).toList();
        }
        return getMapsRepairGroup(repairGroupMain, repairGroups);
    }

    public Optional<StandardTime> getStandardTimeByModification(ModificationAuto modificationAuto) throws NoSuchPaddingException,
                                                               IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String repairCodeDecrypt = RepairCodeDecrypt.repairCodeDecryptCipher(modificationAuto.getRepairCode());
        return standardTimeRepository.getStandardTimeByRepairCode(repairCodeDecrypt);
    }


    public List<Map<String, String>> getMapDataStandardTime(ModificationAuto modificationAuto, RepairGroup repairGroup) throws NoSuchPaddingException,
                                                           IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        List<TypeRepair> typeRepairList = new LinkedList<>();
        List<Double> standardTimeList = new LinkedList<>();
        Optional<StandardTime> optionalStandardTime = getStandardTimeByModification(modificationAuto);
        if (optionalStandardTime.isPresent()) {
            StandardTime standardTime = optionalStandardTime.get();
             typeRepairList = standardTime.getTypeRepairList();
             standardTimeList = standardTime.getStandardTimes();
        }
        return getMapsStandardTime(repairGroup, typeRepairList, standardTimeList);
    }

    private static List<Map<String, String>> getMapsStandardTime(RepairGroup repairGroup, List<TypeRepair> typeRepairList, List<Double> standardTimeList) {
        List<Map<String, String>> mapList = new LinkedList<>();
        for (int i = 0; i< typeRepairList.size(); i++) {
            Map<String, String> map = new HashMap<>();
            if (typeRepairList.get(i).getRepairGroup().getName().equals(repairGroup.getName())) {
                map.put("key", typeRepairList.get(i).getName());
                map.put("value", String.valueOf(standardTimeList.get(i)));
                mapList.add(map);
            }
        }
        if (mapList.isEmpty()) mapList.add(getDefaultMap());
        return mapList;
    }

    private List<Map<String, String>> getMapsRepairGroup(RepairGroupMain repairGroupMain, List<RepairGroup> repairGroups) {
        List<RepairGroup> repairGroupList = repairGroupMainRepository.getByName(repairGroupMain.getName()).get().getRepairGroupList();
        List<Map<String, String>> mapList = new LinkedList<>();
        for (RepairGroup rg : repairGroupList) {
            if (repairGroups.contains(rg)){
                Map<String, String> map = new LinkedHashMap<>();
                map.put("key", rg.getName());
                map.put("value", String.valueOf(rg.getId()));
                mapList.add(map);
            }
        }
        if (mapList.isEmpty()) {
            Map<String, String> map = getDefaultMap();
            mapList.add(map);
        }
        return mapList;
    }

    private static Map<String,String> getDefaultMap(){
        Map<String, String> defaultMap = new HashMap<>();
        defaultMap.put("key", "Данные отсутствуют");
        defaultMap.put("value","-");
        return defaultMap;
    }
}
