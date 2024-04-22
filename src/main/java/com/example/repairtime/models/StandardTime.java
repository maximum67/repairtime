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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "vendorCode")
    private String vendorCode;

    @Column(name="repairCode")
    private String repairCode;

    @Column(name = "standardTime")
    private double standardTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardTime that = (StandardTime) o;
        return id == that.id && Double.compare(that.standardTime, standardTime) == 0 && Objects.equals(vendorCode, that.vendorCode) && Objects.equals(repairCode, that.repairCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vendorCode, repairCode, standardTime);
    }
}


