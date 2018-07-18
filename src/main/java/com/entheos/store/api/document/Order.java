package com.entheos.store.api.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@Document(collection="orders")
@TypeAlias("order")
@JsonInclude(Include.NON_NULL)
public class Order {

	@Id
	@Field("orderId")
    private String orderId;
	
	private List<OrdersLineItem> orderLineItems;
	
	private Float totalAmount;

}
