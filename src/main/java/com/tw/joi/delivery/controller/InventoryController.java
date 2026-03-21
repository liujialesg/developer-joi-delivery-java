package com.tw.joi.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.tw.joi.delivery.service.OutletService;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final OutletService outletService;


    @GetMapping("/health")
    public ResponseEntity<Object> fetchStoreInventoryHealth(@RequestParam(name = "storeId") String storeId) {
        return ResponseEntity.ok(outletService.fetchOutletWithInventory(storeId));
    }
}
