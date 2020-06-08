package com.naijamovies.ratingsdataservice.RestController;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naijamovies.ratingsdataservice.Entity.Rating;
import com.naijamovies.ratingsdataservice.Entity.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {
	
	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId, 4);
	}
	
	@RequestMapping("users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userId) {
		List<Rating> ratings = Arrays.asList(
				new Rating("3", 4),
				new Rating("4", 3));
		UserRating userRating = new UserRating();
		userRating.setUserRating(ratings);
		return userRating;
	}

}
