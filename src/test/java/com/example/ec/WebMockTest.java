package com.example.ec;

import com.example.ec.repo.TourPackageRepository;
import com.example.ec.repo.TourRatingRepository;
import com.example.ec.repo.TourRepository;
import com.example.ec.service.TourPackageService;
import com.example.ec.service.TourRatingService;
import com.example.ec.service.TourService;
import com.example.ec.web.TourRatingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/*
* Test single controller. In this test, Spring Boot instantiates only the web layer rather than the whole context
* */
@WebMvcTest(TourRatingController.class)
public class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TourService tourService;
    @MockBean
    private TourRatingService tourRatingService;
    @MockBean
    private TourPackageService tourPackageService;
    @MockBean
    private TourRepository tourRepository;
    @MockBean
    private TourRatingRepository tourRatingRepository;
    @MockBean
    private TourPackageRepository tourPackageRepository;


    @Test
    public void testMethodCalculateAverageRateFromTourRatingService() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("average", "5");
        when(tourRatingService.calculateAverageRate(1)).thenReturn(map);
        this.mockMvc.perform(get("/tours/1/ratings/average")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("average\":\"5")));
    }
}