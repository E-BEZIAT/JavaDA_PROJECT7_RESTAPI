package com.nnk.springboot.domain.parameters;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class CurvePointParameter {

    @NotNull
    private int id;
    private int curveId;
    private Timestamp asOfDate;
    @NotNull(message = "The field must not be empty")
    private Double term;
    @NotNull(message = "The field must not be empty")
    private Double value;
    private Timestamp creationDate;
}
