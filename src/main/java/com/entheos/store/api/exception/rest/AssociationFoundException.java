package com.entheos.store.api.exception.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Association already exist")
public class AssociationFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
}
