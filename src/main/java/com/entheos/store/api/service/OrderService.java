package com.entheos.store.api.service;

import java.util.List;

import com.entheos.store.api.document.Order;
import com.entheos.store.api.exception.rest.OrderNotFoundException;

public interface OrderService {

	Order save(Order order);

	Order findById(String orderId) throws OrderNotFoundException;

	List<Order> findAll();

	void deleteById(String orderId) throws OrderNotFoundException;

}
