package com.stockapp.service;

import com.stockapp.model.StockPrice;
import com.stockapp.exception.PriceFetchException;
import com.stockapp.repository.StockPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PriceFetcherService implements StockPriceService {

	// Logger for logging info, warnings, and errors
	private static final Logger logger = LoggerFactory.getLogger(PriceFetcherService.class);

	// Repository to interact with database cache of stock prices
    private final StockPriceRepository cacheRepository;
    
    // Builder to create WebClient instances for making HTTP calls
    private final WebClient.Builder webClientBuilder;

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${rapidapi.host}")
    private String rapidApiHost;

    private WebClient rapidApiClient;

    public PriceFetcherService(StockPriceRepository cacheRepository, WebClient.Builder webClientBuilder) {
        this.cacheRepository = cacheRepository;
        this.webClientBuilder = webClientBuilder;
    }

    
    // Initialize WebClient with RapidAPI host after bean is created
    @PostConstruct
    public void init() {
        this.rapidApiClient = webClientBuilder.baseUrl("https://" + rapidApiHost).build();
    }

    // Fetch live price from RapidAPI
    public Double fetchPriceFromRapidApi(String symbol) {
        try {
            String response = rapidApiClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/rapidapi/stock/quote")
                            .queryParam("tradingSymbol", symbol)
                            .queryParam("exchange", "NSE")
                            .build())
                    .header("X-RapidAPI-Key", rapidApiKey)
                    .header("X-RapidAPI-Host", rapidApiHost)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            logger.info("Raw API response for symbol {}: {}", symbol, response);

            if (response == null || response.isEmpty()) {
                logger.warn("Empty response from RapidAPI for symbol: {}", symbol);
                return null;
            }

            // Parse response JSON to extract "lastPrice" field
            JSONObject json = new JSONObject(response);
            if (json.has("lastPrice")) {
                return json.getDouble("lastPrice");
            } else {
                logger.warn("lastPrice not found in RapidAPI response for symbol: {}", symbol);
                return null;
            }
        } catch (Exception e) {
            logger.error("Error fetching price from RapidAPI for symbol: " + symbol, e);
            return null;
        }
    }

    // Get price either from cache or live API if cache is stale or missing
    @Override
    public double getPrice(String symbol) {
        Optional<StockPrice> cacheOptional = cacheRepository.findById(symbol);

        if (cacheOptional.isPresent()) {
            StockPrice cache = cacheOptional.get();
            if (cache.getLastUpdated().isAfter(LocalDateTime.now().minusHours(1))) {
                logger.info("Returning cached price for symbol: {}", symbol);
                return cache.getPrice();
            } else {
                logger.info("Cache expired for symbol: {}", symbol);
            }
        } else {
            logger.info("No cache found for symbol: {}", symbol);
        }

        Double livePrice = fetchPriceFromRapidApi(symbol);

     // Throw exception if live price fetch failed
        if (livePrice == null) {
            throw new PriceFetchException("Failed to fetch live price for symbol: " + symbol);
        }

        StockPrice newCache = new StockPrice();
        newCache.setStockSymbol(symbol);
        newCache.setPrice(livePrice);
        newCache.setLastUpdated(LocalDateTime.now());
        cacheRepository.save(newCache);

        return livePrice;
    }

    // Force refresh price from API, update cache and return live price

    @Override
    public double refreshPrice(String symbol) {
        Double livePrice = fetchPriceFromRapidApi(symbol);

        if (livePrice == null) {
            throw new PriceFetchException("Failed to refresh live price for symbol: " + symbol);
        }

        StockPrice newCache = new StockPrice();
        newCache.setStockSymbol(symbol);
        newCache.setPrice(livePrice);
        newCache.setLastUpdated(LocalDateTime.now());
        cacheRepository.save(newCache);

        return livePrice;
    }

    @Override
    public List<StockPrice> getAllCachedPrices() {
        return cacheRepository.findAll();
    }

    @Override
    public void clearCache() {
        cacheRepository.deleteAll();
        logger.info("Cache cleared");
    }

    // Simple method to check if the RapidAPI is available by trying to fetch price for a known symbol

    @Override
    public boolean isApiAvailable() {
        try {
            // Simple test: try fetching price for a popular symbol like "TATAMOTORS"
            Double price = fetchPriceFromRapidApi("TATAMOTORS");
            return price != null;
        } catch (Exception e) {
            logger.error("API availability check failed", e);
            return false;
        }
    }

    @Override
    public int getCacheSize() {
        return (int) cacheRepository.count();
    }
}