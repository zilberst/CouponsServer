package com.jesus.coupons.beans;


public class UserLoginDetails {

	
	//-------Class Variables---------------------------------------------------------------
	
	
	private String eMail;
	
	private String username;
	
	private String password;


	//--------Constructors-----------------------------------------------------------------
	
	
	public UserLoginDetails() {

	}

	
	//----------Setters & Getters-----------------------------------------------------------

	
	public String geteMail() {
		return eMail;
	}


	public void seteMail(String eMail) {
		this.eMail = eMail;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "UserLoginDetails [EMail=" + eMail + ", username=" + username + ", password=" + password + "]";
	}


}