package com.nnk.springboot.domain.parameters;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RatingParameter {

    private int id;
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private int orderNumber;
}
