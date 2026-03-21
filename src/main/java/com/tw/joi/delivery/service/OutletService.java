package com.tw.joi.delivery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.GroceryStore;
import com.tw.joi.delivery.domain.Outlet;
import com.tw.joi.delivery.seedData.SeedData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OutletService {
	private final Map<String, Outlet> outlets = SeedData.outlets;
	private final List<GroceryProduct> groceryProducts= SeedData.groceryProducts;
	
	/**
	 * Fetch an outlet based on its ID with the details of its inventory filled in.
	 * @param outletId ID of the outlet to fetch.
	 * @return Outlet matching the given ID, with its inventory data filled. 
	 * Returns null if ID does not match any outlets.
	 */
	public Outlet fetchOutletWithInventory(String outletId)
	{
		Outlet outlet = fetchOutletById(outletId);
		
		if (outlet instanceof GroceryStore groceryStore)
		{
			populateGroceryStoreInventory(groceryStore);
		}
		
		return outlet;
	}
	
	/**
	 * Retrieve a specific outlet.
	 * @param outletId ID of the outlet to retrieve.
	 * @return Outlet with the match ID. Null is returned if not found.
	 */
	public Outlet fetchOutletById(String outletId) {
		if (!outlets.containsKey(outletId))
		{
			return null;
		}
		
        return outlets.get(outletId);
    }
	
	/**
	 * Populate the inventory of a given grocery store.
	 * @param storeToPopulate GroceryStore object to populate
	 */
	private void populateGroceryStoreInventory(GroceryStore storeToPopulate)
	{
		List<GroceryProduct> storeProducts = 
				groceryProducts.stream()
		        .filter(product -> product.getStore().getOutletId().equals(storeToPopulate.getOutletId()))
		        .toList();
		
		for(GroceryProduct groceryProduct: storeProducts)
		{
			storeToPopulate.getInventory().add(groceryProduct);
		}
	}
	
}
