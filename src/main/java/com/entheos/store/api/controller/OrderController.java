package com.entheos.store.api.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entheos.store.api.document.Order;
import com.entheos.store.api.document.Product;
import com.entheos.store.api.exception.rest.OrderNotFoundException;
import com.entheos.store.api.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins= {"*.c9users.io", "*"})
@RequestMapping("/api")
@Api(value = "orders", tags = "OrderController")
public class OrderController {
	
	@Resource
	private OrderService orderService;
	
	@ApiOperation(value = "Creates Order", notes = "Creates Order", response = Product.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Product.class),
            @ApiResponse(code = 415, message = "Unsupported Media Type"),
			@ApiResponse(code = 500, message = "Server Error") })
	@RequestMapping(value = "/orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> create(@RequestBody Order order){
		return new ResponseEntity<>(orderService.save(order), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/orders/{orderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> get(@PathVariable("orderId") String orderId) throws OrderNotFoundException{
		return new ResponseEntity<>(orderService.findById(orderId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<Order>> list(){
		List<Order> orders = orderService.findAll();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/orders/{orderId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> delete(@PathVariable("orderId") String orderId) throws OrderNotFoundException{
		orderService.deleteById(orderId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
