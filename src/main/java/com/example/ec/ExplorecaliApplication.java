package com.example.ec;

import com.example.ec.domain.Difficulty;
import com.example.ec.domain.Region;
import com.example.ec.service.TourPackageService;
import com.example.ec.service.TourRatingService;
import com.example.ec.service.TourService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@SpringBootApplication
public class ExplorecaliApplication implements CommandLineRunner {

    @Autowired
    private TourService tourService;

    @Autowired
    private TourRatingService tourRatingService;
    @Autowired
    private TourPackageService tourPackageService;

    public static void main(String[] args) {
        SpringApplication.run(ExplorecaliApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        loadToursAtStartup();
    }

    private void loadToursAtStartup() throws IOException {
        //Create the Tour Packages
        createTourPackages();
        long numOfPackages = tourPackageService.total();

        //Load the tours from an external Json File
        createTours("ExploreCalifornia.json");
        long numOfTours = tourService.total();

        createTourRating();
        long numOfRatings = tourRatingService.total();
    }


    /**
     * Initialize all the known tour packages
     */
    private void createTourPackages() {
        tourPackageService.createTourPackage("BC", "Backpack Cal");
        tourPackageService.createTourPackage("CC", "California Calm");
        tourPackageService.createTourPackage("CH", "California Hot springs");
        tourPackageService.createTourPackage("CY", "Cycle California");
        tourPackageService.createTourPackage("DS", "From Desert to Sea");
        tourPackageService.createTourPackage("KC", "Kids California");
        tourPackageService.createTourPackage("NW", "Nature Watch");
        tourPackageService.createTourPackage("SC", "Snowboard Cali");
        tourPackageService.createTourPackage("TC", "Taste of California");
    }

    private void createTourRating() {
        tourRatingService.createTourRating(1, 5, "Cool", 1);
        tourRatingService.createTourRating(1, 4, "CertoolNot", 2);
        tourRatingService.createTourRating(1, 3, "Certool", 3);
        tourRatingService.createTourRating(1, 2, "Certool", 4);
        tourRatingService.createTourRating(1, 4, "Cool", 5);
        tourRatingService.createTourRating(1, 2, "Coetol", 6);
        tourRatingService.createTourRating(1, 1, "Certool", 7);
        tourRatingService.createTourRating(2, 1, "Cool@", 1);
        tourRatingService.createTourRating(2, 4, "Cool", 2);
        tourRatingService.createTourRating(2, 2, "Cool", 3);
        tourRatingService.createTourRating(2, 1, "Cool", 4);
//        tourRatingService.createTourRating(52, 1, "Cool", 4);

    }

    /**
     * Create tour entities from an external file
     */
    private void createTours(String fileToImport) throws IOException {
        TourFromFile.read(fileToImport).forEach(importedTour ->
                tourService.createTour(importedTour.getTitle(),
                        importedTour.getDescription(),
                        importedTour.getBlurb(),
                        importedTour.getPrice(),
                        importedTour.getLength(),
                        importedTour.getBullets(),
                        importedTour.getKeywords(),
                        importedTour.getPackageType(),
                        importedTour.getDifficulty(),
                        importedTour.getRegion()));
    }

    /**
     * Helper class to import ExploreCalifornia.json
     */
    private static class TourFromFile {
        //fields
        private String packageType, title, description, blurb, price, length, bullets, keywords, difficulty, region;

        //reader
        static List<TourFromFile> read(String fileToImport) throws IOException {
            return new ObjectMapper().setVisibility(FIELD, ANY).
                    readValue(new FileInputStream(fileToImport), new TypeReference<List<TourFromFile>>() {
                    });
        }

        protected TourFromFile() {
        }

        String getPackageType() {
            return packageType;
        }

        String getTitle() {
            return title;
        }

        String getDescription() {
            return description;
        }

        String getBlurb() {
            return blurb;
        }

        Integer getPrice() {
            return Integer.parseInt(price);
        }

        String getLength() {
            return length;
        }

        String getBullets() {
            return bullets;
        }

        String getKeywords() {
            return keywords;
        }

        Difficulty getDifficulty() {
            return Difficulty.valueOf(difficulty);
        }

        Region getRegion() {
            return Region.findByLabel(region);
        }
    }


}
