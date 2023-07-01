package com.banking.application.exception;

public class EmailIdAlreadyExistsException extends Exception{
	public EmailIdAlreadyExistsException() {
		
	}
	public EmailIdAlreadyExistsException(String message) {
		super(message);
	}

}
