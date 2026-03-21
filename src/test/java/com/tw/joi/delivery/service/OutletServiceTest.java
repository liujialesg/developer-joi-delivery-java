package com.tw.joi.delivery.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.GroceryStore;
import com.tw.joi.delivery.domain.Outlet;
import com.tw.joi.delivery.repository.OutletRepository;
import com.tw.joi.delivery.repository.ProductRepository;

@SpringBootTest
public class OutletServiceTest {
	
	
	@Autowired
	private OutletService outletService;
	
	@MockitoBean
	private OutletRepository outletRepository;
	
	@MockitoBean
	private ProductRepository productRepository;
	
	@Test
    void shouldReturnGroceryStoreWithInventoryData() throws Exception {
		String storeID = "store101";
		
        // Mock store
        GroceryStore groceryStore = GroceryStore.builder()
                .name("Fresh Picks")
                .outletId(storeID)
                .build();
        when(outletRepository.fetchOutletById(storeID)).thenReturn(groceryStore);
        
        // Mock products
        GroceryProduct product1 = createGroceryProduct("Wheat Bread", "product101", groceryStore);
        GroceryProduct product2 = createGroceryProduct("Spinach", "product102", groceryStore);
        GroceryProduct product3 = createGroceryProduct("Crackers", "product103", groceryStore);
        
        List<GroceryProduct> groceryProducts =
                Arrays.asList(product1, product2, product3);
        when(productRepository.fetchGroceryProductsByOutletId(storeID)).thenReturn(groceryProducts);
        
        // Execute the method
        Outlet retrievedOutlet = outletService.fetchOutletWithInventory(storeID);
        
        // Must be of Grocery Store class
        GroceryStore retrievedStore = assertInstanceOf(GroceryStore.class, retrievedOutlet);
        
        // Must have correct inventory size
        assertEquals(3, retrievedStore.getInventory().size());
        
        // Must have mocked products
        assert(retrievedStore.getInventory().contains(product1));
        assert(retrievedStore.getInventory().contains(product2));
        assert(retrievedStore.getInventory().contains(product3));
        
	}
	
	
	
	private static GroceryProduct createGroceryProduct(String productName,
            String productId, GroceryStore store) {
				return GroceryProduct.builder()
				.productName(productName)
				.productId(productId)
				.mrp(BigDecimal.valueOf(10.5))
				.weight(BigDecimal.valueOf(500.00))
				.store(store)
				.threshold(10)
				.availableStock(30)
				.build();
		}
}
