package com.naijamovies.moviecatalogservice.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.naijamovies.moviecatalogservice.Entity.CatalogItem;
import com.naijamovies.moviecatalogservice.Entity.Movie;
import com.naijamovies.moviecatalogservice.Entity.Rating;
import com.naijamovies.moviecatalogservice.Entity.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResources {
	
	//call rest template
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		//get all rated movie IDs
		UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
		return ratings.getUserRating().stream()
                .map(rating -> {
                	//Using Rest Template
                	//for each movie ID, call movie info service and get details
                    Movie movie = restTemplate.getForObject("http://movie-info-service:8089/movies/" + rating.getMovieId(), Movie.class);
                  //put them all together
                    return new CatalogItem(movie.getName(), "Description", rating.getRating());
                })
                .collect(Collectors.toList());

	}
}
