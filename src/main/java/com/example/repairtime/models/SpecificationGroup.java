package com.example.repairtime.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SpecificationGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="header_group")
    private String headerGroup;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private SpecificationsCar specificationsCar;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "specificationGroup")
    private List<SpecificationRow> specificationRowList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecificationGroup that = (SpecificationGroup) o;
        return id == that.id && Objects.equals(headerGroup, that.headerGroup) && Objects.equals(specificationsCar, that.specificationsCar) && Objects.equals(specificationRowList, that.specificationRowList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, headerGroup, specificationsCar, specificationRowList);
    }
}
