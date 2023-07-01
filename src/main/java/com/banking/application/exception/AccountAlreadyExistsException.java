package com.banking.application.exception;

public class AccountAlreadyExistsException extends Exception {
	public AccountAlreadyExistsException() {
		super();
	}

	public AccountAlreadyExistsException(String message) {
		super(message);
	}
}