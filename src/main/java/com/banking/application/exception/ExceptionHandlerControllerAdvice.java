package com.banking.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.banking.application.modal.ExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(AccountNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	ExceptionResponse handleAccountNotFoundException(AccountNotFoundException exception, HttpServletRequest request) {
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage(exception.getMessage());
		response.setPath(request.getRequestURI());
		return response;
	}

	@ExceptionHandler(AccountAlreadyExistsException.class)
	@ResponseStatus(value = HttpStatus.FOUND)
	@ResponseBody
	ExceptionResponse handleAccountAlreadyExistsException(AccountAlreadyExistsException exception,
			HttpServletRequest request) {
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage(exception.getMessage());
		response.setPath(request.getRequestURI());
		return response;
	}

	@ExceptionHandler(InsufficientFundsException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	ExceptionResponse handleInsufficientFundsException(InsufficientFundsException exception,
			HttpServletRequest request) {
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage(exception.getMessage());
		response.setPath(request.getRequestURI());
		return response;
	}
	
	@ExceptionHandler(EmailIdAlreadyExistsException.class)
	@ResponseStatus(value = HttpStatus.FOUND)
	@ResponseBody
	ExceptionResponse handleEmailIdAlreadyExistsException(EmailIdAlreadyExistsException exception,
			HttpServletRequest request) {
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage(exception.getMessage());
		response.setPath(request.getRequestURI());
		return response;
	}
}