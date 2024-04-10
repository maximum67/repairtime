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
@Table(name="modification_auto")
public class ModificationAuto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="nameModificationAuto", unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn
    private TypeEngine typeEngine;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "repair_standard",
            joinColumns = {@JoinColumn(name="modification_auto_id")},
            inverseJoinColumns = {@JoinColumn(name="type_repair_id")})
        private List<TypeRepair> typeRepairList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModificationAuto that = (ModificationAuto) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(typeEngine, that.typeEngine) && Objects.equals(typeRepairList, that.typeRepairList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, typeEngine, typeRepairList);
    }
}
