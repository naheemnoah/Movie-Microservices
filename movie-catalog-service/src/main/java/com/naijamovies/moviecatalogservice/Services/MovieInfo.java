package com.naijamovies.moviecatalogservice.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.naijamovies.moviecatalogservice.Entity.CatalogItem;
import com.naijamovies.moviecatalogservice.Entity.MovieSummary;
import com.naijamovies.moviecatalogservice.Entity.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class MovieInfo {

	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(
			fallbackMethod = "getFallbackCatalogItem",
			threadPoolKey = "movieInfoPool",
			threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "20"),
					@HystrixProperty(name = "maxQueueSize", value = "10"),
			})
	public CatalogItem getCatalogItem(Rating rating) {
		MovieSummary movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), MovieSummary.class);
		return new CatalogItem(movie.getTitle(), movie.getOverview(), rating.getRating());
        
	}
	
	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("Movie name not found", "", rating.getRating());
	}
}
