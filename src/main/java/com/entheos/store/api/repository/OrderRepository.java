package com.entheos.store.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.entheos.store.api.document.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
	

}
