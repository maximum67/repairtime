package com.example.repairtime.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "specifications_car")
public class SpecificationsCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="repair_code",unique = true)
    private String repairCode;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "specificationsCar")
    private List<SpecificationGroup> specificationGroupList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecificationsCar that = (SpecificationsCar) o;
        return id == that.id && Objects.equals(repairCode, that.repairCode) && Objects.equals(specificationGroupList, that.specificationGroupList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, repairCode, specificationGroupList);
    }
}
