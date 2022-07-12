package com.example.ec.repo;

import com.example.ec.domain.TourRating;
import com.example.ec.domain.TourRatingPk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, TourRatingPk> {

    List<TourRating> findByPkTourId(int tourId);

    Page<TourRating> findByPkTourId(int tourId, Pageable pageable);

    Optional<TourRating> findByPkTourIdAndPkCustomerId(int tourId, int customerId);

//    void delete(TourRating tourRating);

    List<TourRating> findByPkCustomerId(int customerId);
}