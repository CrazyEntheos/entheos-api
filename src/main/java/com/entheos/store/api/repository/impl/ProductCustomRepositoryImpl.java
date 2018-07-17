package com.entheos.store.api.repository.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;

import com.entheos.store.api.document.Product;
import com.entheos.store.api.dto.BrandSummary;
import com.entheos.store.api.repository.ProductCustomRepository;

public class ProductCustomRepositoryImpl implements ProductCustomRepository{

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<BrandSummary> aggregate(float minPrice, float maxPrice) {
		MatchOperation matchOperation = getMatchOperation(minPrice, maxPrice);
		GroupOperation groupOperation = getGroupOperation();
		ProjectionOperation projectionOperation = getProjectOperation();

		return mongoTemplate.aggregate(Aggregation.newAggregation(
				matchOperation,
				groupOperation,
				projectionOperation
				), Product.class, BrandSummary.class).getMappedResults();
	}

	private MatchOperation getMatchOperation(float minPrice, float maxPrice) {
		return match(where("price").gt(minPrice).andOperator(where("price").lt(maxPrice)));
	}

	private GroupOperation getGroupOperation() {
		return group("brand")
				.last("brand").as("brand")
				.addToSet("id").as("productIds")
				.avg("price").as("averagePrice")
				.sum("price").as("totalRevenue");
	}

	private ProjectionOperation getProjectOperation() {
		return project("productIds", "averagePrice", "totalRevenue")
				.and("warehouse").previousOperation();
	}
}
