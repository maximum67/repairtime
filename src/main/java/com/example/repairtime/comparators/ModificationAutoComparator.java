package com.example.repairtime.comparators;

import com.example.repairtime.models.ModificationAuto;

import java.util.Comparator;

public class ModificationAutoComparator implements Comparator<ModificationAuto> {
    @Override
    public int compare(ModificationAuto o1, ModificationAuto o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
