package com.jesus.coupons.beans;

import com.jesus.coupons.entities.Company;

public class CreateCompany {
	
	private Company company;
	private UserBean user;
	
	public CreateCompany() {
		
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "CreateCompany [company=" + company + ", user=" + user + "]";
	}

}
