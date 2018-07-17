package com.entheos.store.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entheos.store.api.document.Category;
import com.entheos.store.api.document.Product;
import com.entheos.store.api.exception.rest.AssociationFoundException;
import com.entheos.store.api.exception.rest.CategoryNotFoundException;
import com.entheos.store.api.exception.rest.ProductNotFoundException;

@Service
public interface CategoryService {

	List<Category> findAll();

	Category findById(String id) throws CategoryNotFoundException;

	void deleteById(String id) throws CategoryNotFoundException;

	List<Category> findByName(String name);

	Category save(Category category);

	Category update(String id, Category category) throws CategoryNotFoundException;

	List<Product> getProductsByCategoryId(String categoryId) throws CategoryNotFoundException;

	Product getProductByCategoryIdAndProductId(String categoryId, String productId) throws CategoryNotFoundException, ProductNotFoundException;

	void removeProductFromCategory(String categoryId, String productId) throws CategoryNotFoundException, ProductNotFoundException;

	void assignCategoryToProduct(String categoryId, String productId) throws CategoryNotFoundException, ProductNotFoundException, AssociationFoundException;

}
