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
@RequiredArgsConstructor
public class SpecificationGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private SpecificationsGroupName specificationsGroupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private SpecificationsCar specificationsCar;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "specificationGroup")
    private List<SpecificationRow> specificationRowList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecificationGroup that = (SpecificationGroup) o;
        return id == that.id && Objects.equals(specificationsGroupName, that.specificationsGroupName) && Objects.equals(specificationsCar, that.specificationsCar) && Objects.equals(specificationRowList, that.specificationRowList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, specificationsGroupName, specificationsCar, specificationRowList);
    }
}
