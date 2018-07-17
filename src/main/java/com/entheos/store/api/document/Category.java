package com.entheos.store.api.document;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.entheos.store.api.annotation.CascadeSave;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Document(collection="categories")
@TypeAlias("category")
@JsonInclude(Include.NON_NULL)
public class Category {

	@Id
    private String categoryId;

	@Indexed(direction=IndexDirection.ASCENDING)
	@NotNull
	private String categoryName;

	@NotNull
	private String description;
	
	@DBRef
	@CascadeSave
	@JsonManagedReference
	private List<Product> products;
}
