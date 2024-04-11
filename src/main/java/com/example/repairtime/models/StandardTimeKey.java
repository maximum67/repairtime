package com.example.repairtime.models;

import lombok.Data;


import java.io.Serializable;

@Data
public class StandardTimeKey implements Serializable {

    private TypeRepair typeRepairId;

    private ModificationAuto modificationAutoId;

}
