package com.entheos.store.api.service;

import java.util.List;

import com.entheos.store.api.document.Category;
import com.entheos.store.api.document.Product;
import com.entheos.store.api.exception.rest.AssociationFoundException;
import com.entheos.store.api.exception.rest.CategoryNotFoundException;
import com.entheos.store.api.exception.rest.ProductNotFoundException;

public interface ProductService {

	List<Product> findAll();

	Product findById(String productId) throws ProductNotFoundException;

	void deleteById(String productId) throws ProductNotFoundException;

	Product save(Product product);

	Product update(String productId, Product product) throws ProductNotFoundException;

	Category getCategoryByProductId(String productId)throws ProductNotFoundException;

	void removeCategoryFromProduct(String productId) throws ProductNotFoundException;

	void assignCategoryToProduct(String productId, String categoryId) throws ProductNotFoundException, CategoryNotFoundException, AssociationFoundException;

}
