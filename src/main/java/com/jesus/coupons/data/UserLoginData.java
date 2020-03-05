package com.jesus.coupons.data;

import com.jesus.coupons.enums.UserTypes;


public class UserLoginData {
	
	
	//-------Class Variables---------------------------------------------------------------
	
	
	private long id;
	
	private UserTypes userType;
	
	private long companyId;
	

	//--------Constructors------------------------------------------------------------------
	
	
	public UserLoginData(long id, UserTypes userType, long companyId) {
		this(id, userType);
		this.companyId = companyId;
	}

	
	public UserLoginData(long id, UserTypes userType) {
		this.id = id;
		this.userType = userType;
	}

	
	public UserLoginData() {

	}

	
	//----------Setters & Getters-----------------------------------------------------------
	
	
	public long getId() {
		return id;
	}

	
	public void setId(long id) {
		this.id = id;
	}

	
	public UserTypes getUserType() {
		return userType;
	}

	
	public void setUserType(UserTypes userType) {
		this.userType = userType;
	}

	
	public long getCompanyId() {
		return companyId;
	}

	
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	
	@Override
	public String toString() {
		return "UserLoginData [id=" + id + ", userType=" + userType + ", companyId=" + companyId + "]";
	}


}