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
public class RatingParameter {

    @NotNull
    private int id;
    @NotBlank(message = "The field must not be empty")
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String moodysRating;
    @NotBlank(message = "The field must not be empty")
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String sandPRating;
    @NotBlank(message = "The field must not be empty")
    @Size(max = 125, message = "The field must not exceed 125 characters")
    private String fitchRating;
    @NotNull(message = "The field must not be null")
    private int orderNumber;
}
