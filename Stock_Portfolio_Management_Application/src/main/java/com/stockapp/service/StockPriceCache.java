package com.stockapp.service;


import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
public class StockPriceCache {
	
	private final Map<String, BigDecimal> cache = new ConcurrentHashMap<>();
	
	public void updatePrice(String symbol, BigDecimal price) {
		cache.put(symbol,  price);
	}
	
	public BigDecimal getPrice(String symbol) {
		return cache.get(symbol);
	}
	
	// copy to avoid external modifications
	public Map<String, BigDecimal> getAllPrices(){	
		return new ConcurrentHashMap<>(cache);
	}

}
