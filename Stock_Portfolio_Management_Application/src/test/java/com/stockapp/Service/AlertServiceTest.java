package com.stockapp.Service;

import com.stockapp.dto.AlertDTO;
import com.stockapp.exception.AlertNotFoundException;
import com.stockapp.model.Alert;
import com.stockapp.repository.AlertRepository;
import com.stockapp.repository.StockPriceRepository;
import com.stockapp.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlertServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private StockPriceRepository stockPriceRepository;

    @InjectMocks
    private AlertService alertService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAlert() {
        AlertDTO dto = new AlertDTO();
        dto.setSymbol("AAPL");
        dto.setAlertType("Price Above");
        dto.setThresholdValue(150.0);


        Alert saved = new Alert();
        saved.setId(1L);
        saved.setSymbol("AAPL");
        saved.setAlertType("Price Above");
        saved.setThresholdValue(150.0);

        when(alertRepository.save(any(Alert.class))).thenReturn(saved);

        AlertDTO result = alertService.createAlert(dto);

        assertNotNull(result);
        assertEquals("AAPL", result.getSymbol());
        assertEquals("Price Above", result.getAlertType());
        assertEquals(150.0, result.getThresholdValue());

    }

    @Test
    public void testUpdateAlertWhenExists() {
        Alert existing = new Alert();
        existing.setId(1L);
        existing.setSymbol("AAPL");
        existing.setAlertType("Price Above");
        existing.setThresholdValue(150.0);

        AlertDTO dto = new AlertDTO();
        dto.setSymbol("AAPL");
        dto.setAlertType("Price Below");
        dto.setThresholdValue(120.0);

        when(alertRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(alertRepository.save(any(Alert.class))).thenReturn(existing);

        AlertDTO result = alertService.updateAlert(1L, dto);

        assertNotNull(result);
        assertEquals("Price Below", result.getAlertType());
        assertEquals(120.0, result.getThresholdValue());
    }

    @Test
    public void testUpdateAlertWhenNotExists() {
    	AlertDTO dto = new AlertDTO();
    	dto.setSymbol("TSLA");
    	dto.setAlertType("Price Above");
    	dto.setThresholdValue(300.0);

        when(alertRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AlertNotFoundException.class, () -> {
            alertService.updateAlert(99L, dto);
        });

        assertTrue(exception.getMessage().contains("Alert not found with ID"));
    }

    @Test
    public void testEvaluateAlerts() {
        Alert alert = new Alert();
        alert.setId(1L);
        alert.setSymbol("AAPL");
        alert.setAlertType("Price Above");
        alert.setThresholdValue(120.0);

        List<Alert> mockList = Collections.singletonList(alert);

        when(alertRepository.findAll()).thenReturn(mockList);
        when(stockPriceRepository.findByStockSymbol("AAPL"))
                .thenReturn(Optional.of(new com.stockapp.model.StockPrice() {{
                    setStockSymbol("AAPL");
                    setPrice(130.0);
                }}));

        alertService.evaluateAlerts();

        verify(alertRepository, times(1)).findAll();
        verify(stockPriceRepository, times(1)).findByStockSymbol("AAPL");
    }
}
