package com.entheos.store.api.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.entheos.store.api.document.Category;
import com.entheos.store.api.dto.ProductDTO;

@Service
public class ServiceUtils {

	public ServiceUtils() {
	}

	public ProductDTO convertToProductDTO(Category category) {
		ModelMapper modelMapper = new ModelMapper();
		ProductDTO productDTO = modelMapper.map(category, ProductDTO.class);
		return productDTO;
	}

	public List<ProductDTO> converToUserListDTO(List<Category> categoryList) {
		List<ProductDTO> productDTOList = null;
		productDTOList = categoryList.stream().map(category -> convertToProductDTO(category)).collect(Collectors.toList());
		return productDTOList;
	}
	
	public Category convertToCategory(ProductDTO productDTO) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(productDTO, Category.class);
	}
}
