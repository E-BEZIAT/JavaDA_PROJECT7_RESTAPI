package com.nnk.springboot.domain.parameters;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RuleNameParameter {

    @NotNull
    private int id;
    @NotBlank(message = "The field must not be empty")
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String name;
    @NotBlank(message = "The field must not be empty")
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String description;
    @NotBlank(message = "The field must not be empty")
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String json;
    @NotBlank(message = "The field must not be empty")
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String template;
    @NotBlank(message = "The field must not be empty")
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String sqlStr;
    @NotBlank(message = "The field must not be empty")
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String sqlPart;
}
