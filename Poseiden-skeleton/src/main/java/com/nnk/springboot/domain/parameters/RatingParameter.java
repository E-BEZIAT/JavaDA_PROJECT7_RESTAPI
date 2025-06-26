package com.nnk.springboot.domain.parameters;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RatingParameter {

    private String moodysRating;
    private String sandRating;
    private String fitchRating;
    private int orderNumber;
}
