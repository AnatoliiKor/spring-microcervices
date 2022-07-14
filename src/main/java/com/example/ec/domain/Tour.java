package com.example.ec.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Objects;

/**
 * The Tour contains all attributes of an Explore California Tour.
 *
 * Created by Mary Ellen Bowman
 */
@Document
public class Tour {
    @Id
    private String id;

    @Indexed
    private String title;

    private Map<String, String> details;

    @Indexed
    private String tourPackageCode;

    private String tourPackageName;

    public Tour(String title, TourPackage tourPackage, Map<String, String> details) {
        this.title = title;
        this.details = details;
        this.tourPackageCode = tourPackage.getCode();
        this.tourPackageName = tourPackage.getName();
    }

    protected Tour() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    public String getTourPackageCode() {
        return tourPackageCode;
    }

    public void setTourPackageCode(String tourPackageCode) {
        this.tourPackageCode = tourPackageCode;
    }

    public String getTourPackageName() {
        return tourPackageName;
    }

    public void setTourPackageName(String tourPackageName) {
        this.tourPackageName = tourPackageName;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", details=" + details +
                ", tourPackageCode='" + tourPackageCode + '\'' +
                ", tourPackageName='" + tourPackageName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tour tour = (Tour) o;

        if (id != null ? !id.equals(tour.id) : tour.id != null) return false;
        if (title != null ? !title.equals(tour.title) : tour.title != null) return false;
        if (details != null ? !details.equals(tour.details) : tour.details != null) return false;
        if (tourPackageCode != null ? !tourPackageCode.equals(tour.tourPackageCode) : tour.tourPackageCode != null)
            return false;
        return tourPackageName != null ? tourPackageName.equals(tour.tourPackageName) : tour.tourPackageName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (tourPackageCode != null ? tourPackageCode.hashCode() : 0);
        result = 31 * result + (tourPackageName != null ? tourPackageName.hashCode() : 0);
        return result;
    }
}
