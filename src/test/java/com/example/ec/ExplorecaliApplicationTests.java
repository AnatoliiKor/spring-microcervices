package com.example.ec;

import com.example.ec.web.TourRatingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExplorecaliApplicationTests {

	@Autowired
	private TourRatingController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
