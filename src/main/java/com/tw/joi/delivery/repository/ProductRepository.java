package com.tw.joi.delivery.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.seedData.SeedData;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
	private final List<GroceryProduct> groceryProducts= SeedData.groceryProducts;
	
	/**
	 * Retrieve a list of grocery products that a store has given the store's outlet ID.
	 * @param outletId ID of the store that has the grocery products to be retrieved.
	 * @return List of grocery products the indicated store has.
	 */
	public List<GroceryProduct> fetchGroceryProductsByOutletId(String outletId)
	{		
		return groceryProducts.stream()
		        .filter(product -> product.getStore().getOutletId().equals(outletId))
		        .toList();
	}
}
