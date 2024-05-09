package com.example.repairtime.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="repair_standard")
public class StandardTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name="repairCode",unique = true)
    private String repairCode;

    @ManyToMany(
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "type_repairs_repair_code",
            joinColumns = @JoinColumn(name = "repair_standard_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "type_repair_id", referencedColumnName = "id"))
     private List<TypeRepair> typeRepairList;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "standard_time_collection",
            joinColumns = @JoinColumn(name = "standard_time_id"))
    private List<Double> standardTimes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardTime that = (StandardTime) o;
        return id == that.id && Objects.equals(repairCode, that.repairCode) && Objects.equals(typeRepairList, that.typeRepairList) && Objects.equals(standardTimes, that.standardTimes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, repairCode, typeRepairList, standardTimes);
    }
}


