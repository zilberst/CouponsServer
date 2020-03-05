package com.jesus.coupons.enums;

public enum ErrorTypes {

	
	GENERAL_ERROR(601, "GENERAL_ERROR", "General error.", true),
	INVALID_USERNAME(603, "INVALID_USERNAME", "Username already taken.", false),
	INVALID_COMPANY(604, "INVALID_COMPANY", "Company not found.", false),
	INVALID_COMPANY_NAME(605, "INVALID_COMPANY_NAME", "Name already exists.", false),
	INVALID_COUPON(606, "INVALID_COUPON", "Coupon not found.", false),
	INVALID_CUSTOMER(607, "INVALID_CUSTOMER", "Customer not found.", false),
	INVALID_PASSWORD(609, "INVALID_PASSWORD", "Password must be between 8-12 characters, contain a number, a lowercase letter and an uppercase letter.", false), 
	INVALID_AMOUNT_OF_ITEMS(610, "INVALID_AMOUNT_OF_ITEMS", "Amount must be above 0.", false),
	INVALID_COUPON_NAME(611, "INVALID_COUPON_NAME", "Coupon name already taken", false),
	INVALID_PRICE(612, "INVALID_PRICE", "Price must be above 0.", false),
	INVALID_USERTYPE(614, "INVALID_USERTYPE", "Invalid user type.", false),
	INVALID_AMOUNT_OF_KIDS(615, "INVALID_AMOUNT_OF_KIDS", "Amount of kids can't be negative.", false),
	INVALID_USER_ID(616, "INVALID_USER_ID", "User id and customer id doesn't match.", false),
	INVALID_START_DATE(617, "INVALID_START_DATE", "Start date has already passed.", false), 
	INVALID_END_DATE(618, "INVALID_END_DATE", "End date cannot be before the start date.", false),
	MISSING_COMPANY_ID(619, "MISSING_COMPANY_ID", "Company id must be specified for a company user.", false),
	INVALID_LOGIN_DETAILS(621, "INVALID_LOGIN_DETAILS", "Invalid login details.", false), 
	UNAUTHORIZED(622, "UNAUTHORIZED", "Unauthorized access.", false), 
	NOT_ENOUGH_ITEMS_IN_STOCK(623, "NOT_ENOUGH_ITEMS_IN_STOCK", "Not enough items in stock, please choose a different amount.", false), 
	COUPON_OUT_OF_STOCK(624, "COUPON_OUT_OF_STOCK", "Coupon out of stock.", false), 
	INVALID_STOCK(625, "INVALID_STOCK", "Initial stock must be above 0.", false), 
	INVALID_PURCHASE(627, "INVALIDֹֹ_PURCHASE", "Purchase not found.", false), 
	INVALID_USER(628, "INVALID_USER", "User not found.", false), 
	INVALID_EMAIL(629, "INVALID_EMAIL", "E-mail already in use.", false);

	
	private int errorNumber;
	
	private String errorName;
	
	private String errorMessage;
	
	private boolean isShowStackTrace;

	
	private ErrorTypes(int errorNumber, String errorName, String errorMessage, boolean isShowStackTrace) {
		this.errorNumber = errorNumber;
		this.errorName = errorName;
		this.errorMessage = errorMessage;
		this.isShowStackTrace = isShowStackTrace;
	}

	
	public int getErrorNumber() {
		return errorNumber;
	}

	
	public String getErrorName() {
		return errorName;
	}

	
	public String getErrorMessage() {
		return errorMessage;
	}

	
	public boolean isShowStackTrace() {
		return isShowStackTrace;
	}


}