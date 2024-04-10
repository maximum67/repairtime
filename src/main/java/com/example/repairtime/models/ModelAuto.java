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
@Table(name="model_auto")
public class ModelAuto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="nameModel", unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn
    private MarkAuto markAuto;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "modelAuto")
    private List<TypeEngine> typeEngineList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelAuto modelAuto = (ModelAuto) o;
        return id == modelAuto.id && Objects.equals(name, modelAuto.name) && Objects.equals(markAuto, modelAuto.markAuto) && Objects.equals(typeEngineList, modelAuto.typeEngineList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, markAuto, typeEngineList);
    }
}
