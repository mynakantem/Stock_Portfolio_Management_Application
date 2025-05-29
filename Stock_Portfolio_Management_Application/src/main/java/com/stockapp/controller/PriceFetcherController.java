package com.stockapp.controller;

import com.stockapp.model.StockPrice;
import com.stockapp.service.StockPriceService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stock-prices")
public class PriceFetcherController {

    private final StockPriceService stockPriceService;

    public PriceFetcherController(StockPriceService stockPriceService) {
        this.stockPriceService = stockPriceService;
    }

    // Get cached or live price (cached within last 1 hour)
    @GetMapping("/{symbol}")
    public double getPrice(@PathVariable String symbol) {
        return stockPriceService.getPrice(symbol);
    }

    // Get all cached stock prices
    @GetMapping("/cache")
    public List<StockPrice> getAllCachedPrices() {
        return stockPriceService.getAllCachedPrices();
    }

    // Clear the entire cache
    @DeleteMapping("/cache")
    public ResponseEntity<String> clearCache() {
        stockPriceService.clearCache();
        return ResponseEntity.ok("Cache cleared successfully");
    }

    // Force refresh price ignoring cache expiry
    @GetMapping("/{symbol}/refresh")
    public double refreshPrice(@PathVariable String symbol) {
        return stockPriceService.refreshPrice(symbol);
    }

    // Get status of price fetcher service
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        boolean apiAvailable = stockPriceService.isApiAvailable();
        int cacheSize = stockPriceService.getCacheSize();
        String status = "API Available: " + apiAvailable + ", Cache Size: " + cacheSize;
        return ResponseEntity.ok(status);
    }
}
