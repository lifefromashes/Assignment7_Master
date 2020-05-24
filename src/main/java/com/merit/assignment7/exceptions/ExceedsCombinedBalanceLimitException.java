package com.merit.assignment7.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceedsCombinedBalanceLimitException extends Exception {
	public ExceedsCombinedBalanceLimitException(String msg) {
		super(msg);
		
	}

}
