package com.tw.joi.delivery.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.tw.joi.delivery.domain.GroceryStore;
import com.tw.joi.delivery.service.OutletService;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OutletService outletService;

    @Test
    void shouldReturnTheHealthOfTheStore() throws Exception {
        String getUrl = "/inventory/health?storeId={storeId}";
        String storeID = "store101";
        //add required mocking.
        GroceryStore groceryStore = GroceryStore.builder()
                .name("Fresh Picks")
                .outletId(storeID)
                .build();
        when(outletService.fetchOutletWithInventory(storeID)).thenReturn(groceryStore);
        
        mockMvc.perform(MockMvcRequestBuilders.get(getUrl,storeID)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
        //put meaning assertions
           .andExpect(MockMvcResultMatchers.jsonPath("$.outletId", Is.is(storeID)))
           .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("Fresh Picks")))
           .andExpect(MockMvcResultMatchers.jsonPath("$.inventory").isEmpty());
    }
}