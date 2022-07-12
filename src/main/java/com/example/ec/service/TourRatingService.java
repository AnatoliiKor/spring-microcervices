package com.example.ec.service;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourRating;
import com.example.ec.domain.TourRatingPk;
import com.example.ec.repo.TourRatingRepository;
import com.example.ec.repo.TourRepository;
import com.example.ec.web.RatingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TourRatingService {
    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

    @Autowired
    public TourRatingService(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    public void createTourRating(int tourId, int score, String comment, int customerId) {
        tourRatingRepository.save(new TourRating(new TourRatingPk(tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("No such tour")), customerId),score, comment));
    }

    public long total() {
        return tourRatingRepository.count();
    }

    public Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId)
                .orElseThrow(() -> new NoSuchElementException("Tour does not exist " + tourId));
    }

    public TourRating verifyTourRating(int tourId, int consumerId) throws NoSuchElementException {
        return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, consumerId)
                .orElseThrow(() -> new NoSuchElementException("Rating does not exist"));
    }

    public void create(RatingDTO ratingDTO, int tourId) {
        tourRatingRepository.save(new TourRating(new TourRatingPk(verifyTour(tourId), ratingDTO.getCustomerId()),
                ratingDTO.getScore(), ratingDTO.getComment()));
    }

    public RatingDTO editWithPut(RatingDTO ratingDTO, int tourId) {
        TourRating tourRating = verifyTourRating(tourId, ratingDTO.getCustomerId());
        tourRating.setScore(ratingDTO.getScore());
        tourRating.setComment(ratingDTO.getComment());
        return new RatingDTO(tourRatingRepository.save(tourRating));
    }

    public RatingDTO editWithPatch(RatingDTO ratingDTO, int tourId) {
        TourRating tourRating = verifyTourRating(tourId, ratingDTO.getCustomerId());
        if (ratingDTO.getScore() != null) tourRating.setScore(ratingDTO.getScore());
        if (ratingDTO.getComment() != null) tourRating.setComment(ratingDTO.getComment());
        return new RatingDTO(tourRatingRepository.save(tourRating));
    }

    public Map<String, String> calculateAverageRate(int tourId) {
        return Map.of("average", "The tour \"" + verifyTour(tourId).getTitle() + "\" average score is "
                + tourRatingRepository.findByPkTourId(tourId).stream()
                .mapToInt(TourRating::getScore).average()
                .orElseThrow(() -> new NoSuchElementException("Tour has no ratings")));
    }

    public List<String> getRatings(int customerId) {
        return tourRatingRepository.findByPkCustomerId(customerId).stream().map(tourRating ->
                "Tour id=" + tourRating.getPk().getTour().getId() + ". " + tourRating.getPk().getTour().getTitle()
                        + ". Score = " + tourRating.getScore() + ". My comment: " + tourRating.getComment()
        ).collect(Collectors.toList());
    }

    public void deleteRating(int tourId, int customerId) {
        tourRatingRepository.delete(verifyTourRating(tourId, customerId));
    }
}
