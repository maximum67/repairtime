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

    @Column(name="repair_group_name")
    private String repairGroupName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepairGroup that = (RepairGroup) o;
        return id == that.id && repairGroupName.equals(that.repairGroupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, repairGroupName);
    }
}
