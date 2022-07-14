package com.example.ec;

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
import java.util.Map;
import java.util.stream.Collectors;

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
//        createTourPackages();
        long numOfPackages = tourPackageService.total();
        System.out.println(numOfPackages);
        //Load the tours from an external Json File
//        createTours("ExploreCalifornia.json");
        long numOfTours = tourService.total();
        System.out.println(numOfTours);
//        createTourRating();
        long numOfRatings = tourRatingService.total();
        System.out.println(numOfRatings);
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
        tourRatingService.createTourRatingInDb("62cecaf682ca9175d8b166e3", 5, "Cool", 1);
        tourRatingService.createTourRatingInDb("62cecaf682ca9175d8b166e3", 4, "CertoolNot", 2);
        tourRatingService.createTourRatingInDb("62cecaf682ca9175d8b166e3", 3, "Certool", 3);
        tourRatingService.createTourRatingInDb("62cecaf682ca9175d8b166e3", 2, "Certool", 4);
        tourRatingService.createTourRatingInDb("62cecaf682ca9175d8b166e3", 4, "Cool", 5);
        tourRatingService.createTourRatingInDb("62cecaf682ca9175d8b166e3", 2, "Coetol", 6);
        tourRatingService.createTourRatingInDb("62cecaf682ca9175d8b166e3", 1, "Certool", 7);
        tourRatingService.createTourRatingInDb("62cecaf682ca9175d8b166e4", 1, "Cool@", 1);
        tourRatingService.createTourRatingInDb("62cecaf682ca9175d8b166e4", 4, "Cool", 2);
        tourRatingService.createTourRatingInDb("62cecaf682ca9175d8b166e4", 2, "Cool", 3);
        tourRatingService.createTourRatingInDb("62cecaf682ca9175d8b166e4", 1, "Cool", 4);
//        tourRatingService.createTourRating("52", 1, "Cool", 4);

    }

    /**
     * Create tour entities from an external file
     */
    private void createTours(String fileToImport) throws IOException {
        TourFromFile.read(fileToImport).forEach(importedTour ->
                tourService.createTour(importedTour.getTitle(), importedTour.getPackageName(), importedTour.getDetails()));
    }

    /**
     * Helper class to import ExploreCalifornia.json
     */
    private static class TourFromFile {
        //fields
        private String packageName;
        private String title;
        private Map<String, String> details;

        public TourFromFile(Map<String, String> record) {
            this.title = record.get("title");
            this.packageName = record.get("packageType");
            this.details = record;
            this.details.remove("packageType");
            this.details.remove("title");
        }

        //reader
        static List<TourFromFile> read(String fileToImport) throws IOException {
            List<Map<String, String>> records = new ObjectMapper().setVisibility(FIELD, ANY)
                            .readValue(new FileInputStream(fileToImport), new TypeReference<>() {});
            return records.stream().map(TourFromFile::new).collect(Collectors.toList());
        }

        protected TourFromFile() {
        }

        public String getPackageName() {
            return packageName;
        }

        public String getTitle() {
            return title;
        }

        public Map<String, String> getDetails() {
            return details;
        }
    }
}
