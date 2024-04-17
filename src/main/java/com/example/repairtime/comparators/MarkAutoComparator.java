package com.example.repairtime.comparators;

import com.example.repairtime.models.MarkAuto;

import java.util.Comparator;

public class MarkAutoComparator implements Comparator<MarkAuto> {
    @Override
    public int compare(MarkAuto o1, MarkAuto o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
