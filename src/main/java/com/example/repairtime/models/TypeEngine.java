package com.example.repairtime.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name="type_engine")
public class TypeEngine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="nameTypeEngine")
    private String name;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private ModelAuto modelAuto;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "typeEngine")
    private List<ModificationAuto> modificationAutoList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeEngine that = (TypeEngine) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(modelAuto, that.modelAuto) && Objects.equals(modificationAutoList, that.modificationAutoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, modelAuto, modificationAutoList);
    }
}
