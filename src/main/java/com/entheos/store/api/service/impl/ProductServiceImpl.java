package com.entheos.store.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entheos.store.api.document.Category;
import com.entheos.store.api.document.Product;
import com.entheos.store.api.exception.rest.AssociationFoundException;
import com.entheos.store.api.exception.rest.CategoryNotFoundException;
import com.entheos.store.api.exception.rest.ProductNotFoundException;
import com.entheos.store.api.repository.ProductRepository;
import com.entheos.store.api.service.CategoryService;
import com.entheos.store.api.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryService categoryService;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product findById(String productId) throws ProductNotFoundException{
		return productRepository
				.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException(productId));
	}

	@Override
	public void deleteById(String productId) throws ProductNotFoundException {
		productRepository.delete(productRepository
				.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException(productId)));
	}

	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product update(String productId, Product product) throws ProductNotFoundException {
		findById(productId);
		product.setProductId(productId);
		return productRepository.save(product);
	}

	@Override
	public Category getCategoryByProductId(String productId) throws ProductNotFoundException {
		return findById(productId).getCategory();
	}

	@Override
	public void removeCategoryFromProduct(String productId)
			throws ProductNotFoundException {
		Product product = findById(productId);
		Category category = product.getCategory();
		
		if(category != null && category.getProducts() !=null){
			category.getProducts().removeAll(
					category.getProducts().stream()
					.filter(p -> p.getProductId().equalsIgnoreCase(productId)).collect(Collectors.toList()));
			categoryService.save(category);
		} 
		
		if(category != null) {
			product.setCategory(null);
			productRepository.save(product);
		}
	}

	@Override
	public void assignCategoryToProduct(String productId, String categoryId)
			throws ProductNotFoundException, CategoryNotFoundException, AssociationFoundException {
		Product product = findById(productId);
		Category category = categoryService.findById(categoryId);
		product.setCategory(category);
		
		if(category.getProducts() == null) {
			category.setProducts(new ArrayList<>());
		}
		category.getProducts().add(product);
		productRepository.save(product);
		categoryService.save(category);
		
	}

}
