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

    @Column(name="nameModificationAuto")
    private String name;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn
    private TypeEngine typeEngine;

    @Column(name = "repair_code", unique = true)
    private String repairCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModificationAuto that = (ModificationAuto) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(typeEngine, that.typeEngine) && Objects.equals(repairCode, that.repairCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, typeEngine, repairCode);
    }
}
