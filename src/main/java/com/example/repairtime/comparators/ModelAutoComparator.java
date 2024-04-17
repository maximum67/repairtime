package com.example.repairtime.comparators;

import com.example.repairtime.models.ModelAuto;

import java.util.Comparator;

public class ModelAutoComparator implements Comparator<ModelAuto> {
    @Override
    public int compare(ModelAuto o1, ModelAuto o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
