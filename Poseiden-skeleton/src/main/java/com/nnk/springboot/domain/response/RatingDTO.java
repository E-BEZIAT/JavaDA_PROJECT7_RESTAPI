package com.nnk.springboot.domain.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RatingDTO {

    private int id;
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private int orderNumber;

    public RatingDTO(int id, String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
        this.id = id;
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
