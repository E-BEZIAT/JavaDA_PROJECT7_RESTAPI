package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.parameters.RatingParameter;
import com.nnk.springboot.domain.response.RatingDTO;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Permet de créer un Rating
     *
     * @param ratingParameter body à remplir lors de la création d'un Rating
     */
    public void createRating(RatingParameter ratingParameter) {
        Rating rating = new Rating(
                ratingParameter.getMoodysRating(),
                ratingParameter.getSandPRating(),
                ratingParameter.getFitchRating(),
                ratingParameter.getOrderNumber()
        );

        ratingRepository.save(rating);
    }

    /**
     * Permet d'update un Rating
     *
     * @param ratingParameter body à remplir lors de l'update d'un Rating
     * @param id id du Rating à update
     */
    public void updateRating(RatingParameter ratingParameter, int id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found"));

        rating.setMoodysRating(ratingParameter.getMoodysRating());
        rating.setSandPRating(ratingParameter.getSandPRating());
        rating.setFitchRating(ratingParameter.getFitchRating());
        rating.setOrderNumber(ratingParameter.getOrderNumber());

        ratingRepository.save(rating);
    }

    /**
     * Permet de supprimer un Rating
     *
     * @param id id du Rating à supprimer
     */
    public void deleteRating(int id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found"));
        ratingRepository.delete(rating);
    }

    /**
     * Permet de récupèrer et lire un Rating
     *
     * @param id id du Rating à lire
     * @return un objet DTO qui contient les données nécessaires à la vue ou à l’API
     */
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
