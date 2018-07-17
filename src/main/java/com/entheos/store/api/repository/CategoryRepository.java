package com.entheos.store.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.entheos.store.api.document.Category;

@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRepository extends MongoRepository<Category, String> {

	public Optional<Category> findByCategoryId(@Param("id") String id) ;

	public List<Category> findByCategoryName(@Param("name") String name);

}
