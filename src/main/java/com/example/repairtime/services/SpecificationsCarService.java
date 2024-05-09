package com.example.repairtime.services;

import com.example.repairtime.cipher.RepairCodeDecrypt;
import com.example.repairtime.models.*;
import com.example.repairtime.repositories.SpecificationsCarRepository;
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
public class SpecificationsCarService {

    private final SpecificationsCarRepository specificationsCarRepository;

    public List<SpecificationGroup> getSpecificationDataListByModification(ModificationAuto modificationAuto) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String repairCodeDecrypt = RepairCodeDecrypt.repairCodeDecryptCipher(modificationAuto.getRepairCode());
        if (specificationsCarRepository.getOptionalByRepairCode(repairCodeDecrypt).isPresent()) {
            return specificationsCarRepository.getOptionalByRepairCode(repairCodeDecrypt).get().getSpecificationGroupList();
        }else{
            SpecificationGroup specificationGroup = new SpecificationGroup();
            specificationGroup.setHeaderGroup("Данные отсутствуют");
            return Collections.singletonList(specificationGroup);
        }
    }
    public List<SpecificationGroup> getSpecificationDataGroupListByModification(ModificationAuto modificationAuto) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String repairCodeDecrypt = RepairCodeDecrypt.repairCodeDecryptCipher(modificationAuto.getRepairCode());
        if (specificationsCarRepository.getOptionalByRepairCode(repairCodeDecrypt).isPresent()) {
            return specificationsCarRepository.getOptionalByRepairCode(repairCodeDecrypt).get().getSpecificationGroupList();
        }else{
            List <SpecificationGroup> list = new ArrayList<>();
            SpecificationGroup specificationGroup = new SpecificationGroup();
            specificationGroup.setHeaderGroup("Данные отсутствуют");
            list.add(specificationGroup);
            return list;
        }
    }
    public List<Map<String, String>> getSpecificationsMaps(SpecificationGroup specificationGroup) {
        List<Map<String, String>> mapList = new LinkedList<>();
        Map<String, String> defaultMap = new HashMap<>();
        defaultMap.put("key", "Данные отсутствуют");
        for (SpecificationRow specificationRow : specificationGroup.getSpecificationRowList()) {
           Map<String, String> map = new HashMap<>();
                    map.put("key", specificationRow.getSpecificationName()+" "+specificationRow.getSpecificationValue()+" "+specificationRow.getSpecificationUnit());
                    map.put("value", "");
                    mapList.add(map);
                }
        if (mapList.isEmpty()) mapList.add(defaultMap);
        return mapList;
    }
}
