package com.example.ec.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document
public class TourRating {

    @Id
    private String id;

    private String tourId;

    @Min(0)
    @Max(5)
    private int score;

    @Size(max = 255)
    private String comment;

    @NotNull
    private int customerId;

    public TourRating(String tourId, int score, String comment, int customerId) {
        this.tourId = tourId;
        this.score = score;
        this.comment = comment;
        this.customerId = customerId;
    }

    protected TourRating() {
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
