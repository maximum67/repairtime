package com.example.repairtime.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "repair_group_main")
public class RepairGroupMain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name = "groupMainNname")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "repairGroupMain")
    @JoinColumn
    private List<RepairGroup> repairGroupList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepairGroupMain that = (RepairGroupMain) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(repairGroupList, that.repairGroupList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, repairGroupList);
    }
}
