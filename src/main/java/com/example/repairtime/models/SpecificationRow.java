package com.example.repairtime.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name="specification_row")
public class SpecificationRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="specification_name")
    private String specificationName="";

    @Column(name="specification_init")
    private String specificationUnit="";

    @Column(name="specification_value")
    private String specificationValue="";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private SpecificationGroup specificationGroup;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecificationRow that = (SpecificationRow) o;
        return id == that.id && Objects.equals(specificationName, that.specificationName) && Objects.equals(specificationUnit, that.specificationUnit) && Objects.equals(specificationValue, that.specificationValue) && Objects.equals(specificationGroup, that.specificationGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, specificationName, specificationUnit, specificationValue, specificationGroup);
    }
}
