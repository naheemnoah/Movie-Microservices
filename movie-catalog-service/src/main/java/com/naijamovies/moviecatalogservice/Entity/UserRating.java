package com.naijamovies.moviecatalogservice.Entity;

import java.util.List;

public class UserRating {

	public UserRating() {
	}

	private String userId;
	
	private List<Rating> userRating;

	public List<Rating> getUserRating() {
		return userRating;
	}

	public void setUserRating(List<Rating> userRating) {
		this.userRating = userRating;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
