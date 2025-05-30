package com.stockapp.service;
import com.stockapp.model.StockPrice;

import java.util.List;

public interface StockPriceService {
	// Get price with cache check
    double getPrice(String symbol);

    // Force refresh live price and update cache
    double refreshPrice(String symbol);

    // Get all cached stock prices
    List<StockPrice> getAllCachedPrices();

    // Clear the cache
    void clearCache();

    // Check if external API is available (simple check)
    boolean isApiAvailable();

    // Get the cache size (number of cached stock prices)
    int getCacheSize();

}