package com.entheos.store.api.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.entheos.store.api.document.Category;
import com.entheos.store.api.dto.Product;

@Service
public class ServiceUtils {

	public ServiceUtils() {
	}

	public Product convertToProductDTO(Category category) {
		ModelMapper modelMapper = new ModelMapper();
		Product productDTO = modelMapper.map(category, Product.class);
		return productDTO;
	}

	public List<Product> converToUserListDTO(List<Category> categoryList) {
		List<Product> productDTOList = null;
		productDTOList = categoryList.stream().map(category -> convertToProductDTO(category)).collect(Collectors.toList());
		return productDTOList;
	}
	
	public Category convertToCategory(Product productDTO) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(productDTO, Category.class);
	}
}
