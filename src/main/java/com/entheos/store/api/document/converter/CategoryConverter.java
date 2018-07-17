package com.entheos.store.api.document.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import com.entheos.store.api.document.Category;
import com.entheos.store.api.document.Product;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
@WritingConverter
public class CategoryConverter implements Converter<Category, DBObject> {

	@Override
	public DBObject convert(final Category category) {
		final DBObject categoryObject = new BasicDBObject();
		categoryObject.put("_id", category.getCategoryId());
		categoryObject.put("name", category.getCategoryName());
		categoryObject.put("description", category.getDescription());
		if (category.getProducts() != null) {	    	
			List<DBObject> products = category.getProducts()
					.stream().map(p -> convertProduct(p) ).collect(Collectors.toList());     
			categoryObject.put("products", products);

		}
		return categoryObject;
	}

	private DBObject convertProduct(Product product) {
		final DBObject productDbObject = new BasicDBObject();
		if(product.getProductId() !=null) {
			productDbObject.put("_id", product.getProductId());
		}
        productDbObject.put("name", product.getProductName());
        productDbObject.put("description", product.getDescription());
        productDbObject.put("price", product.getPrice());
        productDbObject.put("quantity", product.getQuantity());
		return productDbObject;        
	}

}
