package com.entheos.store.api.exception.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Category not found")
public class CategoryNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException(String id) {
		super("Category " + id + " not found");
	}
}
