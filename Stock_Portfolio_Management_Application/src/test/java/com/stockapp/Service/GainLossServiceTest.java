package com.stockapp.Service;

import com.stockapp.model.*;
import com.stockapp.repository.*;
import com.stockapp.service.GainLossService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GainLossServiceTest {
    
    @Mock
    private GainLossRepository gainLossRepository;
    
    @Mock
    private PortfolioRepository portfolioRepository;
    
    @Mock
    private StockPriceRepository stockPriceRepository;
    
    @InjectMocks
    private GainLossService gainLossService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testCalculateStockGainLoss_Success() {
        // Given
        Long portfolioId = 1L;
        
        // Create test data
        Portfolio portfolio = new Portfolio();
        Holding holding = new Holding();
        holding.setSymbol("AAPL");
        holding.setBuyPrice(100.0);
        holding.setQuantity(10);
        portfolio.setHoldings(Arrays.asList(holding));
        
        StockPrice stockPrice = new StockPrice();
        stockPrice.setPrice(BigDecimal.valueOf(120.0));
        
        // Mock repository calls
        when(portfolioRepository.findById(portfolioId)).thenReturn(Optional.of(portfolio));
        when(stockPriceRepository.findBySymbol("AAPL")).thenReturn(Optional.of(stockPrice));
        
        // When
        List<GainLoss> result = gainLossService.calculateStockGainLoss(portfolioId);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("AAPL", result.get(0).getSymbol());
        assertEquals(200.0, result.get(0).getGainAmount()); // (120-100) * 10
    }
    
    @Test
    void testCalculateStockGainLoss_PortfolioNotFound() {
        // Given
        Long portfolioId = 1L;
        when(portfolioRepository.findById(portfolioId)).thenReturn(Optional.empty());
        
        // When
        List<GainLoss> result = gainLossService.calculateStockGainLoss(portfolioId);
        
        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testCalculateTotalPortfolioGainLoss() {
        // Given
        Long portfolioId = 1L;
        Double expectedGain = 500.0;
        
        // Mock repository calls
        when(portfolioRepository.findById(portfolioId)).thenReturn(Optional.of(new Portfolio()));
        when(gainLossRepository.getTotalPortfolioGain(eq(portfolioId), any(LocalDate.class)))
            .thenReturn(expectedGain);
        
        // When
        Double result = gainLossService.calculateTotalPortfolioGainLoss(portfolioId);
        
        // Then
        assertEquals(expectedGain, result);
    }
    
    @Test
    void testGetDailyPortfolioGainLoss() {
        // Given
        Long portfolioId = 1L;
        LocalDate date = LocalDate.now();
        Double expectedGain = 300.0;
        
        when(gainLossRepository.getTotalPortfolioGain(portfolioId, date)).thenReturn(expectedGain);
        
        // When
        Double result = gainLossService.getDailyPortfolioGainLoss(portfolioId, date);
        
        // Then
        assertEquals(expectedGain, result);
    }
}
