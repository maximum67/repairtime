package com.example.repairtime.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name="modification_auto")
public class ModificationAuto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="nameModificationAuto", unique = true)
    private String nameModificationAuto;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn
    private TypeEngine typeEngine;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModificationAuto that = (ModificationAuto) o;
        return id == that.id && nameModificationAuto.equals(that.nameModificationAuto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameModificationAuto);
    }
}
