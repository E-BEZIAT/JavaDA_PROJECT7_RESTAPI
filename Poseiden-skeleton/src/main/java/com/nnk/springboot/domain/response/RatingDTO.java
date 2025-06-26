package com.nnk.springboot.domain.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RatingDTO {

    private String moodysRating;
    private String sandRating;
    private String fitchRating;
    private int orderNumber;

    public RatingDTO(String moodysRating, String sandRating, String fitchRating, int orderNumber) {
        this.moodysRating = moodysRating;
        this.sandRating = sandRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
