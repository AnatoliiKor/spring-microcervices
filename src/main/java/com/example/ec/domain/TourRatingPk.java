package com.example.ec.domain;

import com.example.ec.web.RatingDTO;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class TourRatingPk implements Serializable {
    @ManyToOne
    private Tour tour;

    @Column(insertable = false, updatable = false, nullable = false)
    private int customerId;

    public TourRatingPk() {
    }

    public TourRatingPk(Tour tour, int customerId) {
        this.tour = tour;
        this.customerId = customerId;
    }

    public Tour getTour() {
        return tour;
    }

    public int getCustomerId() {
        return customerId;
    }
}
