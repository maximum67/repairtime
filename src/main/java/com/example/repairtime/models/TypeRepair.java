package com.example.repairtime.models;

import jakarta.persistence.*;
import lombok.*;

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
    private String name;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private RepairGroup repairGroup;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeRepair that = (TypeRepair) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(repairGroup, that.repairGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, repairGroup);
    }
}
