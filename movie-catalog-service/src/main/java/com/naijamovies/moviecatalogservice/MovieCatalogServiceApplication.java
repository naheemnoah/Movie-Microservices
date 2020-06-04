package com.naijamovies.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class MovieCatalogServiceApplication {

	//create a Rest Template method that spring uses throughout
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	
	//creating web client builder for Reactive Programming
	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}
	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

}
