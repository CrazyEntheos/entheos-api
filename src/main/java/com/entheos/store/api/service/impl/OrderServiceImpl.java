package com.entheos.store.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.entheos.store.api.document.Order;
import com.entheos.store.api.exception.rest.OrderNotFoundException;
import com.entheos.store.api.repository.OrderRepository;
import com.entheos.store.api.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Resource
	private OrderRepository orderRepository;
	
	@Override
	public Order save(Order order) {
		return orderRepository.insert(order);
	}

	@Override
	public Order findById(String orderId) throws OrderNotFoundException {
		return orderRepository
				.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException(orderId));
		
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public void deleteById(String orderId) throws OrderNotFoundException {
		orderRepository.delete(orderRepository
				.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException(orderId)));
		
	}

}
