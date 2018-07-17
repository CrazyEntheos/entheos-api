package com.entheos.store.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.entheos.store.api.document.Cart;

@RepositoryRestResource(collectionResourceRel = "carts", path = "carts")
public interface CartRepository extends MongoRepository<Cart, String> {

	public Optional<Cart> findById(@Param("id") String id);
	
	@Query("{ 'customer': {'$ref': 'customer', '$id': { '$id': ?0 } } }")
	public List<Cart> findByCustomerId(@Param("customerId")String customerId);
}
