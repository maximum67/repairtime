package com.example.repairtime.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="repair_standard")
@IdClass(StandardTimeKey.class)
public class StandardTime {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "modification_auto_id", nullable = false)
    private ModificationAuto modificationAutoId;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "type_repair_id", nullable = false)
    private TypeRepair typeRepairId;

    @Column(name="standardTime")
    private double standardTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardTime that = (StandardTime) o;
        return Double.compare(that.standardTime, standardTime) == 0 && Objects.equals(modificationAutoId, that.modificationAutoId) && Objects.equals(typeRepairId, that.typeRepairId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modificationAutoId, typeRepairId, standardTime);
    }
}
