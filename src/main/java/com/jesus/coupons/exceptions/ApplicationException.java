package com.jesus.coupons.exceptions;

import com.jesus.coupons.enums.ErrorTypes;


public class ApplicationException extends Exception {	

	
	//-------Class Variables---------------------------------------------------------------
	
	
	private static final long serialVersionUID = 1L;
	private ErrorTypes errorType;

	
	//--------Constructors------------------------------------------------------------------
	
	
	public ApplicationException(ErrorTypes errorType, String message) {
		super(message);
		this.errorType = errorType;
	}

	
	public ApplicationException(Exception e, ErrorTypes errorType, String message) {
		super(message, e);
		this.errorType = errorType;
	}

	
	//----------Setters & Getters-----------------------------------------------------------
	
	
	public ErrorTypes getErrorType() {
		return this.errorType;
	}
	
	
}
