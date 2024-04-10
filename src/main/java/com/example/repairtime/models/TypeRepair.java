package com.example.repairtime.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="type_repair")
public class TypeRepair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="type_repair_name")
    private String typeRepairName;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "repair_standard",
//            joinColumns = {@JoinColumn(name="typeRepair_id")},
//            inverseJoinColumns = {@JoinColumn(name="modificationAuto_id")})
//    private List<ModificationAuto> modificationAutoList;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private RepairGroup repairGroup;



}
