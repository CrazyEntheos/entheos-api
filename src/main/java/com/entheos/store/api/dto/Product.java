package com.entheos.store.api.dto;

import java.util.List;

import com.entheos.store.api.document.Category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Product {
	
	@ApiModelProperty(example = "5000001", required=true)
	private String productId;
	@ApiModelProperty(example = "Top BrandName", required = false)
	private String productName;
	@ApiModelProperty(example = "BrandName", required = false)
	private String brand;
	@ApiModelProperty(example = "Sample Description")
	private String description;
	private Integer quantity;
	private Float price;
	private Category category;
	private List<String> size;
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<String> getSize() {
		return size;
	}

	public void setSize(List<String> size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "ProductDTO [productId=" + productId + ", productName=" + productName + ", brand=" + brand
				+ ", quantity=" + quantity + ", price=" + price + ", category=" + category + ", size=" + size + "]";
	}
}
