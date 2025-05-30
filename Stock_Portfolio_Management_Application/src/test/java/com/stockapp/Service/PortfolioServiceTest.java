package com.stockapp.Service;

import com.stockapp.dto.PortfolioDTO;
import com.stockapp.dto.HoldingDTO;
import com.stockapp.model.Portfolio;
import com.stockapp.model.User;
import com.stockapp.repository.PortfolioRepository;
import com.stockapp.repository.UserRepository;
import com.stockapp.service.HoldingService;
import com.stockapp.service.PortfolioService;
import com.stockapp.repository.GainLossRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        PortfolioDTO inputDTO = new PortfolioDTO();
        inputDTO.setName("Tech Stocks");

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        Portfolio savedPortfolio = new Portfolio();
        savedPortfolio.setId(1L);
        savedPortfolio.setName("Tech Stocks");
        savedPortfolio.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(savedPortfolio);

        PortfolioDTO result = portfolioService.createPortfolio(inputDTO, 1L);

        assertNotNull(result);
        assertEquals("Tech Stocks", result.getName());
        assertEquals(1L, result.getUserId());
    }

    @Test
    void testGetPortfolioById() {
        User user = new User();
        user.setId(1L);

        Portfolio portfolio = new Portfolio();
        portfolio.setId(1L);
        portfolio.setName("My Portfolio");
        portfolio.setUser(user);

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));
        when(holdingService.getAllHoldingsForPortfolio(1L)).thenReturn(new ArrayList<>());
        when(gainLossRepository.getTotalPortfolioGain(1L, LocalDate.now())).thenReturn(100.0);

        PortfolioDTO result = portfolioService.getPortfolioById(1L);

        assertNotNull(result);
        assertEquals("My Portfolio", result.getName());
        assertEquals(1L, result.getId());
        assertEquals(100.0, result.getTotalGainAmount());
    }

    @Test
    void testUpdatePortfolio() {
        User user = new User();
        user.setId(1L);

        Portfolio existing = new Portfolio();
        existing.setId(1L);
        existing.setName("Old Name");
        existing.setUser(user);

        Portfolio updated = new Portfolio();
        updated.setId(1L);
        updated.setName("New Name");
        updated.setUser(user);

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(updated);

        PortfolioDTO result = portfolioService.updatePortfolio(1L, "New Name");

        assertNotNull(result);
        assertEquals("New Name", result.getName());
    }

    @Test
    void testDeletePortfolio() {
        when(portfolioRepository.existsById(1L)).thenReturn(true);
        boolean result = portfolioService.deletePortfolio(1L);
        assertTrue(result);
    }

    @Test
    void testIsUserOwnerOfPortfolio() {
        User user = new User();
        user.setId(1L);

        Portfolio portfolio = new Portfolio();
        portfolio.setId(1L);
        portfolio.setUser(user);

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));

        boolean result = portfolioService.isUserOwnerOfPortfolio(1L, 1L);
        assertTrue(result);
    }

    @Test
    void testGetAllPortfoliosForUser() {
        User user = new User();
        user.setId(1L);

        Portfolio portfolio = new Portfolio();
        portfolio.setId(1L);
        portfolio.setName("My Portfolio");
        portfolio.setUser(user);

        List<Portfolio> portfolioList = Collections.singletonList(portfolio);
        List<HoldingDTO> holdings = new ArrayList<>();

        when(portfolioRepository.findByUserId(1L)).thenReturn(portfolioList);
        when(holdingService.getAllHoldingsForPortfolio(1L)).thenReturn(holdings);
        when(gainLossRepository.getTotalPortfolioGain(1L, LocalDate.now())).thenReturn(200.0);

        List<PortfolioDTO> result = portfolioService.getAllPortfoliosForUser(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("My Portfolio", result.get(0).getName());
        assertEquals(200.0, result.get(0).getTotalGainAmount());
    }
}
