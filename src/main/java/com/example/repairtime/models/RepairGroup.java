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
@Table(name="repair_group")
public class RepairGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="repair_group_name", unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "repairGroup")
    private List<TypeRepair> typeRepairList;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private RepairGroupMain repairGroupMain;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepairGroup that = (RepairGroup) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(typeRepairList, that.typeRepairList) && Objects.equals(repairGroupMain, that.repairGroupMain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, typeRepairList, repairGroupMain);
    }
}
