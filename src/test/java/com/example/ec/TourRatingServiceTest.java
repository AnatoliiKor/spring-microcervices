package com.example.ec;

import com.example.ec.repo.TourRatingRepository;
import com.example.ec.repo.TourRepository;
import com.example.ec.service.TourRatingService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

public class TourRatingServiceTest {
    TourRatingService tourRatingService;
    TourRepository tourRepository;
    TourRatingRepository tourRatingRepository;

//    @Before
//    public void init() {
//        tourRatingRepository = mock(TourRatingRepository.class);
//        tourRepository = mock(TourRepository.class);
//    }
//
//    @Test
//    public void calculateValidAverageRate() {
//        tourRatingService = new TourRatingService(tourRatingRepository, tourRepository);
//        int tourId = 1;
//        Assertions.assertEquals(3.5D, tourRatingService.calculateAverageRate(tourId));
//    }


}
