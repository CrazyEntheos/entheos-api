package com.entheos.store.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.entheos.store.api.document.Customer;

@RepositoryRestResource(collectionResourceRel = "customers", path = "customers")
public interface CustomerRepository extends MongoRepository<Customer, String> {

	public Customer findByFirstName(@Param("firstName") String firstName);
	
	public List<Customer> findByLastName(@Param("lastName") String lastName);
}
