package com.example.repairtime.services;

import com.example.repairtime.models.MarkAuto;
import com.example.repairtime.repositories.MarkAutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkAutoService {

    private final MarkAutoRepository markAutoRepository;

    public List<MarkAuto> getMarkAutoList(){

     return markAutoRepository.findAll();
      }
}

