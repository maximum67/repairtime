package com.example.repairtime.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
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

    @Column(name="type_repair_name", unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private RepairGroup repairGroup;

    @Column(name = "vendor_code")
    private String vendorCode;

    @ManyToMany(
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "type_repairs_repair_code",
            joinColumns = @JoinColumn(name = "type_repair_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "repair_standard_id", referencedColumnName = "id"))
    private List<StandardTime> standardTimeList = new LinkedList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeRepair that = (TypeRepair) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(repairGroup, that.repairGroup) && Objects.equals(vendorCode, that.vendorCode) && Objects.equals(standardTimeList, that.standardTimeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, repairGroup, vendorCode, standardTimeList);
    }
}
