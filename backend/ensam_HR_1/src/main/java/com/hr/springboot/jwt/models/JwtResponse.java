package com.hr.springboot.jwt.models;

import com.hr.springboot.userData_module.models.User;

public class JwtResponse {
	 private User user;
	    private String jwtToken;

	    public JwtResponse(User user, String jwtToken) {
	        this.user = user;
	        this.jwtToken = jwtToken;
	    }

	    public User getUser() {
	        return user;
	    }

	    public void setUser(User user) {
	        this.user = user;
	    }

	    public String getJwtToken() {
	        return jwtToken;
	    }

	    public void setJwtToken(String jwtToken) {
	        this.jwtToken = jwtToken;
	    }
}
