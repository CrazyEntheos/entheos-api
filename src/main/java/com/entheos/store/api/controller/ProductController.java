package com.entheos.store.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.entheos.store.api.document.Category;
import com.entheos.store.api.document.Product;
import com.entheos.store.api.exception.rest.AssociationFoundException;
import com.entheos.store.api.exception.rest.CategoryNotFoundException;
import com.entheos.store.api.exception.rest.ProductNotFoundException;
import com.entheos.store.api.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins= {"*.c9users.io", "*"})
@RequestMapping("/api")
@Api(value = "products", tags = "ProductController")
public class ProductController {
	
	private static final String PRODUCTS_URI_PATH = "/products";
	private static final String PRODUCTS_URI_ID_PATH = "/products/{productId}";
	private static final String PROCUCTS_CATEGORIES_PATH= "/products/{productId}/categories";
	private static final String PROCUCTS_CATEGORY_PATH= "/products/{productId}/categories/{categoryId}";
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = PRODUCTS_URI_PATH, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<Product>> list(){
		List<Product> products = productService.findAll();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	@RequestMapping(value = PRODUCTS_URI_ID_PATH, method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Product> get(@PathVariable("productId") String productId) 
			throws ProductNotFoundException {
		return new ResponseEntity<>(productService.findById(productId), HttpStatus.OK);
	}
	
	@RequestMapping(value = PRODUCTS_URI_ID_PATH, method = RequestMethod.DELETE, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("productId") String productId) 
			throws ProductNotFoundException {
		productService.deleteById(productId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "Creates Product", notes = "Creates Product", response = Product.class, responseContainer = "List")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Product.class),
            @ApiResponse(code = 415, message = "Unsupported Media Type"),
			@ApiResponse(code = 500, message = "Server Error") })
	@RequestMapping(value = PRODUCTS_URI_PATH, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> create(@RequestBody Product product){
		return new ResponseEntity<>(productService.save(product), HttpStatus.OK);
	}
	
	@RequestMapping(value = PRODUCTS_URI_ID_PATH, method = RequestMethod.PUT,  
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("productId") String productId, @RequestBody Product product) 
			throws ProductNotFoundException{
		return new ResponseEntity<>(productService.update(productId, product), HttpStatus.OK);
	}
	
	@RequestMapping(value = PROCUCTS_CATEGORIES_PATH, method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Category> listCategories(@PathVariable("productId") String productId) 
			throws ProductNotFoundException {
		return new ResponseEntity<>(productService.getCategoryByProductId(productId), HttpStatus.OK);
	}
	
	@RequestMapping(value = PROCUCTS_CATEGORIES_PATH, method = RequestMethod.DELETE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> removeCategoryFromProduct( 
			@PathVariable("productId") String productId) throws ProductNotFoundException {
		productService.removeCategoryFromProduct(productId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = PROCUCTS_CATEGORY_PATH, method = RequestMethod.PUT, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Product> assignCategoryToProduct(@PathVariable("productId") String productId, 
			@PathVariable("categoryId") String categoryId) 
					throws ProductNotFoundException, CategoryNotFoundException, AssociationFoundException {
		productService.assignCategoryToProduct(productId, categoryId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
