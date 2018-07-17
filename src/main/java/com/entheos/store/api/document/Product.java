package com.entheos.store.api.document;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.entheos.store.api.annotation.CascadeSave;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="products")
@TypeAlias("product")
@JsonInclude(Include.NON_NULL)
public class Product {

	@Id
	@Field("productId")
    private String productId;

	@Indexed(direction=IndexDirection.ASCENDING)
	@NotNull
	private String productName;
	
	@Indexed(direction=IndexDirection.ASCENDING)
	@NotNull
	private String brand;

	private String description;

	private Integer quantity;

	@NotNull
	private Float price;
	
	@DBRef
	@CascadeSave
	@JsonBackReference
	private Category category;
	
	private List<String> size;

}
