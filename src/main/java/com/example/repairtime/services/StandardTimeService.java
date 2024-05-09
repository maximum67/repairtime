package com.example.repairtime.services;

import com.example.repairtime.cipher.RepairCodeDecrypt;
import com.example.repairtime.models.*;
import com.example.repairtime.repositories.StandardTimeRepository;
import com.example.repairtime.repositories.TypeRepairRepository;
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
public class StandardTimeService {

    private final StandardTimeRepository standardTimeRepository;
    private final TypeRepairRepository typeRepairRepository;


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

    public Optional<StandardTime> getStandardTimeByModification(ModificationAuto modificationAuto) throws NoSuchPaddingException,
                                                                                                       IllegalBlockSizeException,
                                                                                                       NoSuchAlgorithmException,
                                                                                                       BadPaddingException,
                                                                                                       InvalidKeyException {
        String repairCodeDecrypt = RepairCodeDecrypt.repairCodeDecryptCipher(modificationAuto.getRepairCode());
        return standardTimeRepository.getStandardTimeByRepairCode(repairCodeDecrypt);
    }


    public List<Map<String, String>> getMapDataStandardTime(ModificationAuto modificationAuto, RepairGroup repairGroup) throws NoSuchPaddingException,
                                                                                                                              IllegalBlockSizeException,
                                                                                                                              NoSuchAlgorithmException,
                                                                                                                              BadPaddingException,
                                                                                                                              InvalidKeyException {
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
