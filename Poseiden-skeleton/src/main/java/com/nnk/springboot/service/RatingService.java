package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.parameters.RatingParameter;
import com.nnk.springboot.domain.response.RatingDTO;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public void createRating(RatingParameter ratingParameter) {

        Rating rating = new Rating(
                ratingParameter.getMoodysRating(),
                ratingParameter.getSandPRating(),
                ratingParameter.getFitchRating(),
                ratingParameter.getOrderNumber()
        );

        ratingRepository.save(rating);
    }

    public void updateRating(RatingParameter ratingParameter, int id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found"));

        rating.setMoodysRating(ratingParameter.getMoodysRating());
        rating.setSandPRating(ratingParameter.getSandPRating());
        rating.setFitchRating(ratingParameter.getFitchRating());
        rating.setOrderNumber(ratingParameter.getOrderNumber());
        ratingRepository.save(rating);
    }

    public void deleteRating(int id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found"));
        ratingRepository.delete(rating);
    }

    public RatingDTO readRating(int id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found"));

        return new RatingDTO(
                rating.getId(),
                rating.getMoodysRating(),
                rating.getSandPRating(),
                rating.getFitchRating(),
                rating.getOrderNumber()
        );
    }
}
