package com.example.repairtime.services;

import com.example.repairtime.models.ModificationAuto;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Parser {


    public static ModificationAuto parsingString(List<String> strings){

        List<String> listMark = new LinkedList<>();
        List<String> listModel = new LinkedList<>();
        List<String> listEngineType = new LinkedList<>();
        List<String> listModification = new LinkedList<>();

        for (String string : strings) {
            string = string.replaceAll(" / ", "*");
            List<String> list = new LinkedList<>(Arrays.asList(string.split("\\*")));
            listMark.add(list.get(0));
            listModel.add(list.get(1));
            listEngineType.add(list.get(2));
            listModification.add(list.get(3));
        }
        ModificationAuto modificationAuto = new ModificationAuto();
            modificationAuto.setNameModificationAuto(listModification.get(1));
           return modificationAuto;




//        listMark.forEach(System.out::println);
//        System.out.println();
//        listModel.forEach(System.out::println);
//        System.out.println();
//        listEngineType.forEach(System.out::println);
//        System.out.println();
//        listModification.forEach(System.out::println);
//        System.out.println();
    }

}
