package com.example.repairtime.models;

import jakarta.persistence.*;
import lombok.*;

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

}
