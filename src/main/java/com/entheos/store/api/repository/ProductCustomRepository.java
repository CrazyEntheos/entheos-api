package com.entheos.store.api.repository;

import java.util.List;

import com.entheos.store.api.dto.BrandSummary;

public interface ProductCustomRepository {

	List<BrandSummary> aggregate(float minPrice, float maxPrice);
}
