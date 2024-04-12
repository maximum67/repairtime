package com.example.repairtime.services;

import com.example.repairtime.models.TypeRepair;
import com.example.repairtime.repositories.TypeRepairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeRepaireService {
    private final TypeRepairRepository typeRepairRepository;
}
