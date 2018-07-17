package com.entheos.store.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
import com.entheos.store.api.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RestController
@CrossOrigin(origins= {"*.c9users.io", "*"})
@RequestMapping("/api")
@Api(value = "categories", tags = "CategoryController")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<Category>> list(){
		List<Category> categories = categoryService.findAll();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Category> get(@PathVariable("categoryId") String categoryId) 
			throws CategoryNotFoundException {
		return new ResponseEntity<>(categoryService.findById(categoryId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.DELETE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("categoryId") String categoryId) 
			throws CategoryNotFoundException {
		categoryService.deleteById(categoryId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "Creates Category", notes = "Creates Category", response = Category.class, responseContainer = "List")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Category.class),
            @ApiResponse(code = 415, message = "Unsupported Media Type"),
			@ApiResponse(code = 500, message = "Server Error") })
	@RequestMapping(value = "/categories", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Category> create(@RequestBody Category category){
		return new ResponseEntity<>(categoryService.save(category), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.PUT,  
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("categoryId") String categoryId, @RequestBody Category category) 
			throws CategoryNotFoundException{
		return new ResponseEntity<>(categoryService.update(categoryId, category), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/categories/{categoryId}/products", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<Product>> listProducts(@PathVariable("categoryId") String categoryId) 
			throws CategoryNotFoundException {
		return new ResponseEntity<>(categoryService.getProductsByCategoryId(categoryId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/categories/{categoryId}/products/{productId}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Product> getProduct(@PathVariable("categoryId") String categoryId, 
			@PathVariable("productId") String productId) throws CategoryNotFoundException, ProductNotFoundException {
		return new ResponseEntity<>(categoryService.getProductByCategoryIdAndProductId(categoryId, productId), 
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/categories/{categoryId}/products/{productId}", method = RequestMethod.DELETE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> removeProductFromCategory(
			@PathVariable("categoryId") String categoryId, 
			@PathVariable("productId") String productId) throws CategoryNotFoundException, ProductNotFoundException {
		categoryService.removeProductFromCategory(categoryId,productId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/categories/{categoryId}/products/{productId}", method = RequestMethod.PUT, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> assignCategoryToProduct(@PathVariable("categoryId") String categoryId, 
			@PathVariable("productId") String productId) 
					throws CategoryNotFoundException, ProductNotFoundException, AssociationFoundException {
		categoryService.assignCategoryToProduct(categoryId,productId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
