package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(length = 125)
    private String moodysRating;
    @Column(length = 125)
    private String sandPRating;
    @Column(length = 125)
    private String fitchRating;

    private int orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    public Rating() {}

}
