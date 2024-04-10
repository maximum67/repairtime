package com.example.repairtime.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class StandardTimeKey implements Serializable {

    @JdbcTypeCode(SqlTypes.BIGINT)
    @Column(name="type_repair_id")
    private TypeRepair typeRepair;


    @JdbcTypeCode(SqlTypes.BIGINT)
    @Column(name="modification_auto_id")
    private ModificationAuto modificationAuto;
}
