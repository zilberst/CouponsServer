package com.jesus.coupons.beans;

import com.jesus.coupons.entities.Company;
import com.jesus.coupons.enums.UserTypes;


public class UserBean {
	

	//-------Class Variables---------------------------------------------------------------

	
	private long id;

	private String firstName;

	private String lastName;

	private String eMail;

	private String username;

	private String password;

	private UserTypes userType;

	private Company company;


	//--------Constructors-----------------------------------------------------------------

	
	public UserBean() {

	}

	
	//----------Setters & Getters-----------------------------------------------------------

	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
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


	public UserTypes getUserType() {
		return userType;
	}


	public void setUserType(UserTypes userType) {
		this.userType = userType;
	}


	public Company getCompany() {
		return company;
	}


	public void setCompany(Company company) {
		this.company = company;
	}

	
	@Override
	public String toString() {
		return "UserBean [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", EMail=" + eMail
				+ ", username=" + username + ", password=" + password + ", userType=" + userType + ", company="
				+ company + "]";
	}

	
}