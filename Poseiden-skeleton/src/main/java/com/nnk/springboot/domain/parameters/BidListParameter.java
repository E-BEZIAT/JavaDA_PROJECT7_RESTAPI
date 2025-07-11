package com.nnk.springboot.domain.parameters;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class BidListParameter {

    @NotNull
    private int bidListId;
    @NotBlank(message = "The field must not be empty")
    @Size(max = 30, message = "The field must not exceed 30 characters")
    private String account;
    @NotBlank(message = "The field must not be empty")
    @Size(max = 30, message = "The field must not exceed 30 characters")
    private String type;
    @NotNull
    private Double bidQuantity;
    private Double askQuantity;
    private Double bid;
    private Double ask;
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String benchmark;
    private Timestamp bidListDate;
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String commentary;
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String security;
    @Size(max = 10, message = "The field must not exceed 10 characters")
    private String status;
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String trader;
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String book;
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String creationName;
    private Timestamp creationDate;
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String revisionName;
    private Timestamp revisionDate;
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String dealName;
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String dealType;
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String sourceListId;
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String side;

    public BidListParameter() {}
}
