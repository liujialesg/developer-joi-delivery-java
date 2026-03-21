package com.tw.joi.delivery.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.tw.joi.delivery.domain.GroceryStore;
import com.tw.joi.delivery.domain.Outlet;
import com.tw.joi.delivery.seedData.SeedData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OutletService {
	private final Map<String, Outlet> outlets = SeedData.outlets;
	
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
	
	private GroceryStore populateInventory(GroceryStore storeToPopulate)
	{
		// TODO
		return null;
	}
	
}
