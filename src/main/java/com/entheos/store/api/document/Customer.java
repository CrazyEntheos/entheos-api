package com.entheos.store.api.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.entheos.store.api.annotation.CascadeSave;
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
@Document(collection="customers")
@TypeAlias("customer")
@JsonInclude(Include.NON_NULL)
public class Customer {

	@Id
	private String id;

	private String firstName;
	
	private String lastName;

	@DBRef
	@CascadeSave
	private List<Cart> carts;

}
