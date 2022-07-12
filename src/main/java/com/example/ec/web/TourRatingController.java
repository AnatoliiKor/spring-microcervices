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
    public List<RatingDTO> getTourRatings(@PathVariable(value = "tourId") int tourId) {
        tourRatingService.verifyTour(tourId);
        return tourRatingRepository.findByPkTourId(tourId).stream().map(RatingDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/page")
    public Page<RatingDTO> getTourRatingsPage(@PathVariable(value = "tourId") int tourId, Pageable pageable) {
        tourRatingService.verifyTour(tourId);
        Page<TourRating> rating = tourRatingRepository.findByPkTourId(tourId, pageable);
// pageable and total number is used for navigation
        return new PageImpl<>(
                rating.get().map(RatingDTO::new).collect(Collectors.toList()),
                pageable,
                rating.getTotalElements()
        );
    }

    @GetMapping("/p")
    public Page<RatingDTO> getTourRatingsP(@PathVariable(value = "tourId") int tourId, Pageable pageable) {
        tourRatingService.verifyTour(tourId);
        Page<TourRating> rating = tourRatingRepository.findByPkTourId(tourId, pageable);

        return new PageImpl<>(
                rating.get().map(RatingDTO::new).collect(Collectors.toList())
        );
    }

    @GetMapping("/{customerId}")
    public TourRating getTourRating(@PathVariable(value = "tourId") int tourId,
                                    @PathVariable(value = "customerId") int customerId) {
        return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException("No customer or tour"));
    }

    @GetMapping("/{customerId}/all")
    public List<String> getToursRating(@PathVariable(value = "customerId") int customerId) {
        return tourRatingService.getRatings(customerId);
    }

    @GetMapping("/average")
    public Map<String, String> getTourRatingAverage(@PathVariable(value = "tourId") int tourId) {
        return tourRatingService.calculateAverageRate(tourId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@Valid @RequestBody RatingDTO ratingDTO,
                                 @PathVariable(value = "tourId") int tourId) {
        tourRatingService.create(ratingDTO, tourId);
    }

    @PutMapping
    public RatingDTO editTourRatingWithPut(@Valid @RequestBody RatingDTO ratingDTO,
                                           @PathVariable(value = "tourId") int tourId) {
        return tourRatingService.editWithPut(ratingDTO, tourId);
    }

    @PatchMapping
    public RatingDTO editTourRatingWithPatch(@Valid @RequestBody RatingDTO ratingDTO,
                                             @PathVariable(value = "tourId") int tourId) {
        return tourRatingService.editWithPatch(ratingDTO, tourId);
    }

    @DeleteMapping("/{customerId}")
    public List<String> deleteTourRating(@PathVariable(value = "tourId") int tourId,
                                         @PathVariable(value = "customerId") int customerId) {
        tourRatingService.deleteRating(tourId, customerId);
        return tourRatingService.getRatings(customerId);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
