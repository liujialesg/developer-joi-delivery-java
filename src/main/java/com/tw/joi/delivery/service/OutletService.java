package com.tw.joi.delivery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.GroceryStore;
import com.tw.joi.delivery.domain.Outlet;
import com.tw.joi.delivery.repository.OutletRepository;
import com.tw.joi.delivery.repository.ProductRepository;
import com.tw.joi.delivery.seedData.SeedData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OutletService {

	private final OutletRepository outletRepository;
	private final ProductRepository productRepository;
	
	/**
	 * Fetch an outlet based on its ID with the details of its inventory filled in.
	 * @param outletId ID of the outlet to fetch.
	 * @return Outlet matching the given ID, with its inventory data filled. 
	 * Returns null if ID does not match any outlets.
	 */
	public Outlet fetchOutletWithInventory(String outletId)
	{
		Outlet outlet = outletRepository.fetchOutletById(outletId);
		
		if (outlet instanceof GroceryStore groceryStore)
		{
			populateGroceryStoreInventory(groceryStore);
		}
		
		return outlet;
	}
	
	/**
	 * Populate the inventory of a given grocery store.
	 * @param storeToPopulate GroceryStore object to populate
	 */
	private void populateGroceryStoreInventory(GroceryStore storeToPopulate)
	{
		List<GroceryProduct> storeProducts = productRepository
				.fetchGroceryProductsByOutletId(storeToPopulate.getOutletId());
		
		for(GroceryProduct groceryProduct: storeProducts)
		{
			storeToPopulate.getInventory().add(groceryProduct);
		}
	}
	
}
