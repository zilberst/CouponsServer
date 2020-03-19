package com.jesus.coupons.data;

import com.jesus.coupons.enums.UserTypes;


public class SuccessfulLoginData {

	
	//-------Class Variables---------------------------------------------------------------
	
	
	private long token;
	
	private UserTypes userType;
	
	private String firstName;
	
	private String lastName;


	//--------Constructors------------------------------------------------------------------
	
	
	public SuccessfulLoginData(long token, UserTypes userType, String firstName, String lastName) {
		this.token = token;
		this.userType = userType;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	
	//----------Setters & Getters-----------------------------------------------------------
	

	public UserTypes getUserType() {
		return userType;
	}


	public long getToken() {
		return token;
	}


	public void setToken(long token) {
		this.token = token;
	}


	public void setUserType(UserTypes userType) {
		this.userType = userType;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	@Override
	public String toString() {
		return "SuccessfulLoginData [token=" + token + ", userType=" + userType + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}


}