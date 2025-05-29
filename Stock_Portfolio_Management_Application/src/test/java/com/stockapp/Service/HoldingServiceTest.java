package com.stockapp.Service;

import com.stockapp.service.HoldingService;
import com.stockapp.service.PortfolioService;
import com.stockapp.dto.HoldingDTO;
import com.stockapp.model.Holding;
import com.stockapp.model.Portfolio;
import com.stockapp.repository.HoldingRepository;
import com.stockapp.repository.PortfolioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HoldingServiceTest {
    
    @Mock
    private HoldingRepository holdingRepository;
    
    @Mock
    private PortfolioRepository portfolioRepository;
    
    @InjectMocks
    private HoldingService holdingService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testAddHolding() {
        // Create test data
        HoldingDTO inputDTO = new HoldingDTO();
        inputDTO.setSymbol("AAPL");
        inputDTO.setQuantity(10);
        inputDTO.setBuyPrice(150.00);
        inputDTO.setPortfolioId(1L);
        
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1L);
        portfolio.setName("Tech Portfolio");
        
        Holding savedHolding = new Holding();
        savedHolding.setId(1L);
        savedHolding.setSymbol("AAPL");
        savedHolding.setQuantity(10);
        savedHolding.setBuyPrice(150.00);
        savedHolding.setPortfolio(portfolio);
        
        // Mock behavior
        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));
        when(holdingRepository.save(any(Holding.class))).thenReturn(savedHolding);
        
        // Call method
        HoldingDTO result = holdingService.addHolding(inputDTO);
        
        // Verify
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("AAPL", result.getSymbol());
        assertEquals(10, result.getQuantity());
        assertEquals(150.00, result.getBuyPrice());
    }
    
    @Test
    void testGetHoldingById() {
        // Create test data
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1L);
        
        Holding holding = new Holding();
        holding.setId(1L);
        holding.setSymbol("AAPL");
        holding.setQuantity(10);
        holding.setBuyPrice(150.00);
        holding.setPortfolio(portfolio);
        
        // Mock behavior
        when(holdingRepository.findById(1L)).thenReturn(Optional.of(holding));
        
        // Call method
        HoldingDTO result = holdingService.getHoldingById(1L);
        
        // Verify
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("AAPL", result.getSymbol());
        assertEquals(10, result.getQuantity());
        assertEquals(150.00, result.getBuyPrice());
    }
    
    @Test
    void testUpdateHolding() {
        // Create test data
        HoldingDTO updateDTO = new HoldingDTO();
        updateDTO.setQuantity(20);
        updateDTO.setBuyPrice(145.00);
        
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1L);
        
        Holding existingHolding = new Holding();
        existingHolding.setId(1L);
        existingHolding.setSymbol("AAPL");
        existingHolding.setQuantity(10);
        existingHolding.setBuyPrice(150.00);
        existingHolding.setPortfolio(portfolio);
        
        Holding updatedHolding = new Holding();
        updatedHolding.setId(1L);
        updatedHolding.setSymbol("AAPL");
        updatedHolding.setQuantity(20);
        updatedHolding.setBuyPrice(145.00);
        updatedHolding.setPortfolio(portfolio);
        
        // Mock behavior
        when(holdingRepository.findById(1L)).thenReturn(Optional.of(existingHolding));
        when(holdingRepository.save(any(Holding.class))).thenReturn(updatedHolding);
        
        // Call method
        HoldingDTO result = holdingService.updateHolding(1L, updateDTO);
        
        // Verify
        assertNotNull(result);
        assertEquals(20, result.getQuantity());
        assertEquals(145.00, result.getBuyPrice());
    }
    
    @Test
    void testDeleteHolding() {
        // Mock behavior
        when(holdingRepository.existsById(1L)).thenReturn(true);
        
        // Call method
        boolean result = holdingService.deleteHolding(1L);
        
        // Verify
        assertTrue(result);
        verify(holdingRepository, times(1)).deleteById(1L);
    }
}