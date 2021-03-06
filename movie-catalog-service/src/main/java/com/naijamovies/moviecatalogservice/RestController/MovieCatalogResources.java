package com.naijamovies.moviecatalogservice.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.naijamovies.moviecatalogservice.Entity.CatalogItem;
import com.naijamovies.moviecatalogservice.Entity.MovieSummary;
import com.naijamovies.moviecatalogservice.Entity.Rating;
import com.naijamovies.moviecatalogservice.Entity.UserRating;
import com.naijamovies.moviecatalogservice.Services.MovieInfo;
import com.naijamovies.moviecatalogservice.Services.UserRatingInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResources {
	
	//call rest template
	@Autowired
	private RestTemplate restTemplate;
	
	//Reactive programming instead of RestTemplate
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	//Advance LoadBalancing
//	@Autowired
//	private DiscoveryClient discoveryClient; 
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		//get all rated movie IDs
		UserRating ratings = userRatingInfo.getUserRating(userId);
				return ratings.getUserRating().stream()
                .map(rating -> {
                	return movieInfo.getCatalogItem(rating);
               })
                .collect(Collectors.toList());
	}
	
	
		
}
