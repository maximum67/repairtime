package com.example.repairtime.comparators;

import com.example.repairtime.models.TypeEngine;

import java.util.Comparator;

public class TypeEngineComparator implements Comparator<TypeEngine> {
    @Override
    public int compare(TypeEngine o1, TypeEngine o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
