package com.naijamovies.moviecatalogservice.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.naijamovies.moviecatalogservice.Entity.CatalogItem;
import com.naijamovies.moviecatalogservice.Entity.Movie;
import com.naijamovies.moviecatalogservice.Entity.MovieSummary;
import com.naijamovies.moviecatalogservice.Entity.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class MovieInfo {

	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating) {
		MovieSummary movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), MovieSummary.class);
		return new CatalogItem(movie.getTitle(), movie.getOverview(), rating.getRating());
        
	}
	
	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("Movie name not found", "", rating.getRating());
	}
}
