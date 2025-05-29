package com.stockapp.ServiceTest;

import com.stockapp.exception.PriceFetchException;
import com.stockapp.model.StockPrice;
import com.stockapp.repository.StockPriceRepository;
import com.stockapp.service.PriceFetcherService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockPriceServiceTest {

    private StockPriceRepository mockRepository;
    private WebClient.Builder mockWebClientBuilder;
    private PriceFetcherService priceFetcherService;

    @BeforeEach
    public void setup() {
        mockRepository = mock(StockPriceRepository.class);
        mockWebClientBuilder = mock(WebClient.Builder.class);  

        priceFetcherService = new PriceFetcherService(mockRepository, mockWebClientBuilder);
    }

    @Test
    public void testReturnsCachedValueWhenCacheIsFresh() {
        StockPrice cached = new StockPrice();
        cached.setStockSymbol("TCS");
        cached.setPrice(150.0);
        cached.setLastUpdated(LocalDateTime.now().minusMinutes(10)); // fresh

        when(mockRepository.findById("TCS")).thenReturn(Optional.of(cached));

        double price = priceFetcherService.getPrice("TCS");

        assertEquals(150.0, price);
    }

    @Test
    public void testThrowsExceptionWhenNoCacheAndApiFails() {
        when(mockRepository.findById("TCS")).thenReturn(Optional.empty());

        // You might mock fetchPriceFromRapidApi if needed, but by default it will return null and throw exception
        assertThrows(PriceFetchException.class, () -> priceFetcherService.getPrice("TCS"));
    }
}
