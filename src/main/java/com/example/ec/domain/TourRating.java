package com.example.ec.domain;

import javax.persistence.*;

@Entity
public class TourRating {
    @EmbeddedId
    private TourRatingPk pk;

    @Column(nullable = false)
    private int score;

    @Column
    private String comment;

    public TourRating(TourRatingPk pk, int score, String comment) {
        this.pk = pk;
        this.score = score;
        this.comment = comment;
    }

    protected TourRating() {
    }

    public TourRatingPk getPk() {
        return pk;
    }

    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }

    public void setPk(TourRatingPk pk) {
        this.pk = pk;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
