package com.stockapp.Service;
import com.stockapp.service.HoldingService;
import com.stockapp.service.PortfolioService;
import com.stockapp.dto.PortfolioDTO;
import com.stockapp.model.Portfolio;
import com.stockapp.model.User;
import com.stockapp.repository.PortfolioRepository;
import com.stockapp.repository.UserRepository;
import com.stockapp.repository.GainLossRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PortfolioServiceTest {
    
    @Mock
    private PortfolioRepository portfolioRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private GainLossRepository gainLossRepository;
    
    @Mock
    private HoldingService holdingService;
    
    @InjectMocks
    private PortfolioService portfolioService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testCreatePortfolio() {
        // Create test data
        PortfolioDTO inputDTO = new PortfolioDTO();
        inputDTO.setName("Tech Stocks");
        
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        
        Portfolio savedPortfolio = new Portfolio();
        savedPortfolio.setId(1L);
        savedPortfolio.setName("Tech Stocks");
        savedPortfolio.setUser(user);
        
        // Mock behavior
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(savedPortfolio);
        
        // Call method
        PortfolioDTO result = portfolioService.createPortfolio(inputDTO, 1L);
        
        // Verify
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Tech Stocks", result.getName());
        assertEquals(1L, result.getUserId());
    }
    
    @Test
    void testGetAllPortfoliosForUser() {
        // Create test data
        User user = new User();
        user.setId(1L);
        
        Portfolio portfolio1 = new Portfolio();
        portfolio1.setId(1L);
        portfolio1.setName("Portfolio 1");
        portfolio1.setUser(user);
        
        Portfolio portfolio2 = new Portfolio();
        portfolio2.setId(2L);
        portfolio2.setName("Portfolio 2");
        portfolio2.setUser(user);
        
        List<Portfolio> portfolios = Arrays.asList(portfolio1, portfolio2);
        
        // Mock behavior
        when(portfolioRepository.findByUserId(1L)).thenReturn(portfolios);
        when(gainLossRepository.getTotalPortfolioGain(1L, LocalDate.now())).thenReturn(100.0);
        when(gainLossRepository.getTotalPortfolioGain(2L, LocalDate.now())).thenReturn(200.0);
        
        // Call method
        List<PortfolioDTO> result = portfolioService.getAllPortfoliosForUser(1L);
        
        // Verify
        assertEquals(2, result.size());
        assertEquals("Portfolio 1", result.get(0).getName());
        assertEquals("Portfolio 2", result.get(1).getName());
    }
    
    @Test
    void testGetPortfolioById() {
        // Create test data
        User user = new User();
        user.setId(1L);
        
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1L);
        portfolio.setName("My Portfolio");
        portfolio.setUser(user);
        
        // Mock behavior
        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));
        when(gainLossRepository.getTotalPortfolioGain(1L, LocalDate.now())).thenReturn(150.0);
        
        // Call method
        PortfolioDTO result = portfolioService.getPortfolioById(1L);
        
        // Verify
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("My Portfolio", result.getName());
        assertEquals(150.0, result.getTotalGainAmount());
    }
    
    @Test
    void testUpdatePortfolio() {
        // Create test data
        User user = new User();
        user.setId(1L);
        
        Portfolio existingPortfolio = new Portfolio();
        existingPortfolio.setId(1L);
        existingPortfolio.setName("Old Name");
        existingPortfolio.setUser(user);
        
        Portfolio updatedPortfolio = new Portfolio();
        updatedPortfolio.setId(1L);
        updatedPortfolio.setName("New Name");
        updatedPortfolio.setUser(user);
        
        // Mock behavior
        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(existingPortfolio));
        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(updatedPortfolio);
        
        // Call method
        PortfolioDTO result = portfolioService.updatePortfolio(1L, "New Name");
        
        // Verify
        assertNotNull(result);
        assertEquals("New Name", result.getName());
    }
    
    @Test
    void testDeletePortfolio() {
        // Mock behavior
        when(portfolioRepository.existsById(1L)).thenReturn(true);
        
        // Call method
        boolean result = portfolioService.deletePortfolio(1L);
        
        // Verify
        assertTrue(result);
    }
    
    @Test
    void testIsUserOwnerOfPortfolio() {
        // Create test data
        User user = new User();
        user.setId(1L);
        
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1L);
        portfolio.setUser(user);
        
        // Mock behavior
        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));
        
        // Call method
        boolean result = portfolioService.isUserOwnerOfPortfolio(1L, 1L);
        
        // Verify
        assertTrue(result);
    }
}