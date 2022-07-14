package com.example.ec.web;

import com.example.ec.domain.TourRating;
import com.example.ec.repo.TourRatingRepository;
import com.example.ec.repo.TourRepository;
import com.example.ec.service.TourRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tours/{tourId}/ratings")
public class TourRatingController {
    TourRatingRepository tourRatingRepository;
    TourRepository tourRepository;

    TourRatingService tourRatingService;

    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository, TourRatingService tourRatingService) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
        this.tourRatingService = tourRatingService;
    }

    protected TourRatingController() {
    }

    @GetMapping
    public List<TourRating> getTourRatings(@PathVariable(value = "tourId") String tourId) {
        tourRatingService.verifyTour(tourId);
        return tourRatingRepository.findByTourId(tourId);
    }

    @GetMapping("/page")
    public Page<TourRating> getTourRatingsPage(@PathVariable(value = "tourId") String tourId, Pageable pageable) {
        return tourRatingRepository.findByTourId(tourId, pageable);
    }

    @GetMapping("/{customerId}")
    public TourRating getTourRating(@PathVariable(value = "tourId") String tourId,
                                    @PathVariable(value = "customerId") int customerId) {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException("No customer or tour"));
    }

//    @GetMapping("/{customerId}/all")
//    public List<String> getToursRating(@PathVariable(value = "customerId") int customerId) {
//        return tourRatingService.getRatings(customerId);
//    }

    @GetMapping("/average")
    public Map<String, String> getTourRatingAverage(@PathVariable(value = "tourId") String tourId) {
        return tourRatingService.calculateAverageRate(tourId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@Valid @RequestBody TourRating tourRating,
                                 @PathVariable(value = "tourId") String tourId) {
        tourRatingService.verifyTour(tourId);
        tourRatingService.createTourRating(tourRating, tourId);
    }

    @PutMapping
    public TourRating editTourRatingWithPut(@Valid @RequestBody TourRating tourRating,
                                           @PathVariable(value = "tourId") int tourId) {
        return tourRatingService.editWithPut(tourRating);
    }

    @PatchMapping
    public TourRating editTourRatingWithPatch(@Valid @RequestBody TourRating tourRating,
                                             @PathVariable(value = "tourId") int tourId) {
        return tourRatingService.editWithPatch(tourRating);
    }

//    @DeleteMapping("/{customerId}")
//    public List<String> deleteTourRating(@PathVariable(value = "tourId") int tourId,
//                                         @PathVariable(value = "customerId") int customerId) {
//        tourRatingService.deleteRating(tourId, customerId);
//        return tourRatingService.getRatings(customerId);
//    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
