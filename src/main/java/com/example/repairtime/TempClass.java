package com.example.repairtime;

import com.example.repairtime.services.ParsingFile;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TempClass implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ParsingFile parsingFile = new ParsingFile();
        Map<Integer, List<String>> map = parsingFile.read("test.xlsx");
        List<String> resultList = new LinkedList<>();
        map.forEach((key, value) -> resultList.add(value.get(1)));
//        Parser.parsingString(resultList);


    }
}
