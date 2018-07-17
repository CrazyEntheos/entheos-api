package com.entheos.store.api.document.converter;

import org.springframework.core.convert.converter.Converter;

import com.entheos.store.api.document.Product;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

//@Component
//@WritingConverter 
public class ProductConverter implements Converter<Product, DBObject>{

	@Override
	public DBObject convert(Product product) {
		final DBObject productObject = new BasicDBObject();
		productObject.put("_id", product.getProductId());
		productObject.put("name", product.getProductName());
		productObject.put("description", product.getDescription());
		productObject.put("price", product.getPrice());
		productObject.put("quantity", product.getQuantity());
		if (product.getCategory() != null) {	    	
			productObject.put("categoryId", product.getCategory().getCategoryId());
			productObject.put("categoryName", product.getCategory().getCategoryName());
		}
		return productObject;
	}

}
