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

    @Column(name="nameMark")
    private String nameMark;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "markAuto")
    private List<ModelAuto> modelAutoList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkAuto markAuto = (MarkAuto) o;
        return id == markAuto.id && nameMark.equals(markAuto.nameMark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameMark);
    }
}
