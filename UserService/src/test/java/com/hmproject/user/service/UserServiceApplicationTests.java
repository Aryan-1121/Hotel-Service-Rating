package com.hmproject.user.service;

import com.hmproject.user.service.entities.Rating;
import com.hmproject.user.service.external.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}


	@Autowired
	private RatingService ratingService;

	@Test
	void createRating(){
		Rating rating = Rating.builder().rating(10).userId("").hotelId("").feedback("this is feedbackk").build();
		ResponseEntity<Rating> savedRating = ratingService.createRating(rating);
		System.out.println("RATING created -> " + savedRating.getStatusCode());
	}

}
