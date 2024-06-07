package com.example.repairtime.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="specifications_group_name")
public class SpecificationsGroupName {

          @Id
          @GeneratedValue(strategy = GenerationType.IDENTITY)
          @Column
          private long id;

          @Column
          private String name;

}
