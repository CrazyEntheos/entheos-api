package com.entheos.store.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entheos.store.api.document.Category;
import com.entheos.store.api.document.Product;
import com.entheos.store.api.exception.rest.AssociationFoundException;
import com.entheos.store.api.exception.rest.CategoryNotFoundException;
import com.entheos.store.api.exception.rest.ProductNotFoundException;
import com.entheos.store.api.repository.CategoryRepository;
import com.entheos.store.api.service.CategoryService;
import com.entheos.store.api.service.ProductService;

@Service
public class CategoryServiceImpl implements CategoryService {
	

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductService productService;

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category findById(String id) throws CategoryNotFoundException {
		return categoryRepository
				.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException(id));
	}

	@Override
	public void deleteById(String id) throws CategoryNotFoundException {
		categoryRepository.delete(categoryRepository
				.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException(id)));
	}

	@Override
	public List<Category> findByName(String name) {
		return categoryRepository.findByCategoryName(name);
	}
	
	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}
	
	@Override
	public Category update(String id, Category category) throws CategoryNotFoundException{
		findById(id);
		category.setCategoryId(id);
		return categoryRepository.save(category);
	}
	
	@Override
	public List<Product> getProductsByCategoryId(String categoryId) throws CategoryNotFoundException {
		return findById(categoryId).getProducts();
	}

	@Override
	public Product getProductByCategoryIdAndProductId(String categoryId, String productId) throws CategoryNotFoundException,
	ProductNotFoundException {
		return findById(categoryId).getProducts().stream()
				.filter(p -> p.getProductId().equalsIgnoreCase(productId))
				.findFirst().orElseThrow(() -> new ProductNotFoundException(productId));
	}

	@Override
	public void removeProductFromCategory(String categoryId, String productId) throws CategoryNotFoundException, 
												ProductNotFoundException {
		Category category = findById(categoryId);
		Product product = category.getProducts().stream()
				.filter(p -> p.getProductId().equalsIgnoreCase(productId))
				.findAny().orElseThrow(() -> new ProductNotFoundException(productId));

		category.getProducts().remove(product);
		
		categoryRepository.save(category);
		
		product.setCategory(null);
		
		productService.save(product);
	}

	@Override
	public void assignCategoryToProduct(String categoryId, String productId) throws CategoryNotFoundException, ProductNotFoundException, AssociationFoundException {
		Category category = findById(categoryId);
		Product product = productService.findById(productId);
		if(category.getProducts().stream().noneMatch(p -> p.getProductId().equalsIgnoreCase(productId))) {
			category.getProducts().add(product);
		}
		
		product.setCategory(category);
		
		categoryRepository.save(category);
		productService.save(product);
		
	}

}
