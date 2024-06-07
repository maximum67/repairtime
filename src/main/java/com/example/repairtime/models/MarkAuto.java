package com.example.repairtime.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name="mark_auto")
public class MarkAuto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="nameMark",unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "markAuto")
    private List<ModelAuto> modelAutoList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkAuto markAuto = (MarkAuto) o;
        return id == markAuto.id && Objects.equals(name, markAuto.name) && Objects.equals(modelAutoList, markAuto.modelAutoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, modelAutoList);
    }
}
