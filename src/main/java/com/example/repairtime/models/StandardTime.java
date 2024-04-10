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
public class StandardTime {

    @EmbeddedId
    private StandardTimeKey id;

    @Column(name="standardTime")
    private double standardTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardTime that = (StandardTime) o;
        return Double.compare(that.standardTime, standardTime) == 0 && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, standardTime);
    }
}
