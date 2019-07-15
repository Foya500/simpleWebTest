package com.foya.dto;

public class UserDTO {
	private String userId;
	private String name;
	private String email;
	 
	
	  public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	    public String toString() {
	        return String.format(
	                "UserDTO[userId='%s', name='%s', email='%s', password='%s', Date='%s']",
	                userId, name, email);
	    }
}
