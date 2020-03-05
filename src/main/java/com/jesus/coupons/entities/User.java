package com.jesus.coupons.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jesus.coupons.enums.UserTypes;


@Entity
@Table(name="USERS")
public class User {

	
	//-------Class Variables---------------------------------------------------------------

	
	@Id
	@GeneratedValue
	private long id;

	@Column(name="FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name="LAST_NAME", nullable = false)
	private String lastName;

	@Column(name="EMAIL", unique = true, nullable = false)
	private String eMail;

	@Column(name="USERNAME", unique = true, nullable = false)
	private String username;

	@Column(name="PASSWORD", nullable = false)
	private String password;

	@Column(name="USER_TYPE", nullable = false)
	private UserTypes userType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Company company;


	//--------Constructors-----------------------------------------------------------------

	
	public User() {

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
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", eMail=" + eMail
				+ ", username=" + username + ", password=" + password + ", userType=" + userType + ", company="
				+ company + "]";
	}


}