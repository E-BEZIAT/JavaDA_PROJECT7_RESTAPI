package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;


@Entity
@Getter
@Setter
@ToString
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(name = "CurveId")
    private int curveId;

    private Timestamp asOfDate;

    private Double term;

    private Double value;

    private Timestamp creationDate;

    public CurvePoint(int curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public CurvePoint(int curveId, Timestamp asOfDate, Double term, Double value, Timestamp creationDate) {
        this.curveId = curveId;
        this.asOfDate = asOfDate;
        this.term = term;
        this.value = value;
        this.creationDate = creationDate;
    }

    public CurvePoint() {
    }
}
