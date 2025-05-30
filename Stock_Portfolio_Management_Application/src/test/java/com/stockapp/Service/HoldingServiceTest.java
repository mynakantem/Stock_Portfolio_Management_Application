package com.stockapp.Service;

import com.stockapp.dto.HoldingDTO;
import com.stockapp.model.Holding;
import com.stockapp.model.Portfolio;
import com.stockapp.repository.HoldingRepository;
import com.stockapp.repository.PortfolioRepository;
import com.stockapp.service.HoldingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HoldingServiceTest {

    @Mock
    private HoldingRepository holdingRepository;

    @Mock
    private PortfolioRepository portfolioRepository;

    @InjectMocks
    private HoldingService holdingService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddHolding() {
        HoldingDTO dto = new HoldingDTO();
        dto.setSymbol("AAPL");
        dto.setQuantity(5);
        dto.setBuyPrice(120.0);
        dto.setPortfolioId(1L);

        Portfolio portfolio = new Portfolio();
        portfolio.setId(1L);

        Holding holding = new Holding();
        holding.setId(1L);
        holding.setSymbol("AAPL");
        holding.setQuantity(5);
        holding.setBuyPrice(120.0);
        holding.setPortfolio(portfolio);

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));
        when(holdingRepository.save(any())).thenReturn(holding);

        HoldingDTO result = holdingService.addHolding(dto);

        assertNotNull(result);
        assertEquals("AAPL", result.getSymbol());
        assertEquals(5, result.getQuantity());
    }
}
