package com.example.ec.service;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourPackage;
import com.example.ec.repo.TourPackageRepository;
import com.example.ec.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TourService {

    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    /**
     * Create a new Tour Object and persist it to the Database.
     *
     * @param title           title of the tour
     * @param tourPackageName tour package name
     * @param details         details about tour (key-value pairs)
     * @return Tour Entity
     */
    public Tour createTour(String title, String tourPackageName, Map<String, String> details) {

        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour package not found " + tourPackageName));
        return tourRepository.save(new Tour(title, tourPackage, details));
    }

    /**
     * Calculate the number of Tours in the Database.
     *
     * @return the total.
     */
    public long total() {
        return tourRepository.count();
    }
}
