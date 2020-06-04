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
		
		
//		WebClient.Builder
		
		//get all rated movie IDs
		List<Rating> ratings = Arrays.asList(
				new Rating("1234", 4),
				new Rating("5789", 3));
				
		//for each movie ID, call movie info service and get details
		
		//put them all together
		return ratings.stream()
                .map(rating -> {
                	//Using Rest Template
//                    Movie movie = restTemplate.getForObject("http://localhost:8089/movies/" + rating.getMovieId(), Movie.class);
                    
                	//Using WebClient
                	Movie movie = webClientBuilder.build()
                			.get()
                			.uri("http://localhost:8089/movies/" + rating.getMovieId())
                			.retrieve().bodyToMono(Movie.class).block();
                	return new CatalogItem(movie.getName(), "Description", rating.getRating());
                })
                .collect(Collectors.toList());

	}
}
