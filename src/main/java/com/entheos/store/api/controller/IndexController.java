package com.entheos.store.api.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@CrossOrigin(origins= {"*.c9users.io", "*"})
@ApiIgnore
public class IndexController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	void redirectToSwaggerUi(HttpServletResponse response) throws IOException {
		response.sendRedirect("swagger-ui.html");
	}
}
