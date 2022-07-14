package com.example.ec.service;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourRating;
import com.example.ec.repo.TourRatingRepository;
import com.example.ec.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class TourRatingService {
    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

    @Autowired
    public TourRatingService(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    public void createTourRatingInDb(String tourId, int score, String comment, int customerId) {
        tourRatingRepository.save(new TourRating(tourId, score, comment, customerId));
    }

    public void createTourRating(TourRating tourRating, String tourId) {
        tourRatingRepository.save(new TourRating(tourId, tourRating.getScore(), tourRating.getComment(),
                tourRating.getCustomerId()));
    }

    public long total() {
        return tourRatingRepository.count();
    }

    public void verifyTour(String tourId) throws NoSuchElementException {
        if (!tourRepository.existsById(tourId)) throw new NoSuchElementException("Tour does not exist " + tourId);
    }

    public TourRating verifyTourRating(String tourId, int consumerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, consumerId)
                .orElseThrow(() -> new NoSuchElementException("Rating does not exist"));
    }

    public TourRating editWithPut(TourRating rating) {
        TourRating tourRating = verifyTourRating(rating.getTourId(), rating.getCustomerId());
        tourRating.setScore(rating.getScore());
        tourRating.setComment(rating.getComment());
        return tourRatingRepository.save(tourRating);
    }

    public TourRating editWithPatch(TourRating rating) {
        TourRating tourRating = verifyTourRating(rating.getTourId(), rating.getCustomerId());
        if (rating.getScore() != null) tourRating.setScore(rating.getScore());
        if (rating.getComment() != null) tourRating.setComment(rating.getComment());
        return tourRatingRepository.save(tourRating);
    }

    public Map<String, String> calculateAverageRate(String tourId) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new NoSuchElementException("Tour does not exist " + tourId));
        return Map.of("average", "The tour \"" + tour.getTitle() + "\" average score is "
                + tourRatingRepository.findByTourId(tourId).stream()
                .mapToInt(TourRating::getScore).average()
                .orElseThrow(() -> new NoSuchElementException("Tour has no ratings")));
    }

//    public List<String> getRatings(int customerId) {
//        return tourRatingRepository.findByPkCustomerId(customerId).stream().map(tourRating ->
//                "Tour id=" + tourRating.getPk().getTour().getId() + ". " + tourRating.getPk().getTour().getTitle()
//                        + ". Score = " + tourRating.getScore() + ". My comment: " + tourRating.getComment()
//        ).collect(Collectors.toList());
//    }

    public void deleteRating(String tourId, int customerId) {
        tourRatingRepository.delete(verifyTourRating(tourId, customerId));
    }
}
