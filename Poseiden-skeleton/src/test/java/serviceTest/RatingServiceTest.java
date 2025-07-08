package serviceTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.parameters.RatingParameter;
import com.nnk.springboot.domain.response.RatingDTO;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RatingService ratingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ratingService = new RatingService(ratingRepository);
    }

    @Test
    public void createRatingTest() {
        RatingParameter ratingParameter = new RatingParameter();
        ratingParameter.setMoodysRating("Moodys Rating");
        ratingParameter.setSandPRating("Sand PRating");
        ratingParameter.setFitchRating("Fitch Rating");
        ratingParameter.setOrderNumber(10);

        Rating savedRating = new Rating(
                "Moodys Rating",
                "sand PRating",
                "Fitch Rating",
                10
        );

        when(ratingRepository.save(any(Rating.class))).thenReturn(savedRating);

        ratingService.createRating(ratingParameter);

        verify(ratingRepository, times(1)).save(argThat(rating ->
                rating.getMoodysRating().equals("Moodys Rating") &&
                rating.getSandPRating().equals("Sand PRating") &&
                rating.getFitchRating().equals("Fitch Rating") &&
                rating.getOrderNumber() == 10
        ));
    }

    @Test
    public void updateRatingTest() {
        Rating rating = new Rating(
                "Moodys Rating",
                "sand PRating",
                "Fitch Rating",
                10
        );

        RatingParameter ratingParameter = new RatingParameter();
        ratingParameter.setMoodysRating("Moodys");
        ratingParameter.setSandPRating("Sand");
        ratingParameter.setFitchRating("Fitch");
        ratingParameter.setOrderNumber(20);

        int id = 1;

        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));

        ratingService.updateRating(ratingParameter, id);

        verify(ratingRepository, times(1)).findById(id);
        verify(ratingRepository, times(1)).save(rating);
        assertEquals("Moodys", rating.getMoodysRating());
        assertEquals("Sand", rating.getSandPRating());
        assertEquals("Fitch", rating.getFitchRating());
        assertEquals(20, rating.getOrderNumber());
    }

    @Test
    public void deleteRatingTest() {
        Rating rating = new Rating(
                "Moodys Rating",
                "sand PRating",
                "Fitch Rating",
                10
        );

        int id = 1;

        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));

        ratingService.deleteRating(id);

        verify(ratingRepository, times(1)).findById(id);
        verify(ratingRepository, times(1)).delete(rating);
    }

    @Test
    public void readRatingTest() {
        Rating rating = new Rating(
                "Moodys Rating",
                "sand PRating",
                "Fitch Rating",
                10
        );

        int id = 1;

        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));

        RatingDTO result = ratingService.readRating(id);

        assertNotNull(result);
        assertEquals("Moodys Rating", result.getMoodysRating());
        assertEquals("sand PRating", result.getSandPRating());
        assertEquals("Fitch Rating", result.getFitchRating());
        assertEquals(10, result.getOrderNumber());
        verify(ratingRepository, times(1)).findById(id);
    }
}
