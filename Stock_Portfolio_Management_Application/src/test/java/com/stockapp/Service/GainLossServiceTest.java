package com.stockapp.Service;

import com.stockapp.service.GainLossService;
import com.stockapp.repository.HoldingRepository;
import com.stockapp.repository.StockPriceRepository;
import com.stockapp.repository.GainLossRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GainLossServiceTest {

    @Mock
    private GainLossRepository gainLossRepository;

    @InjectMocks
    private GainLossService gainLossService;

    @Test
    public void testGetTotalPortfolioGain() {
        // Arrange
        Long portfolioId = 1L;
        LocalDate date = LocalDate.now();
        Double expectedGain = 500.0;
        
        when(gainLossRepository.getTotalPortfolioGain(portfolioId, date)).thenReturn(expectedGain);
        
        // Act
        Double actualGain = gainLossService.getTotalPortfolioGain(portfolioId, date);
        
        // Assert
        assertEquals(expectedGain, actualGain);
    }
}