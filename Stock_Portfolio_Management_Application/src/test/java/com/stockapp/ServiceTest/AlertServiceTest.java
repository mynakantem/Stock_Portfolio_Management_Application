
package com.stockapp.serviceTest;

import com.stockapp.service.AlertService;
import com.stockapp.dto.AlertDTO;
import com.stockapp.model.Alert;
import com.stockapp.repository.AlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlertServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private AlertService alertService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // initialize mocks
    }

    @Test
    public void testCreateAlert() {
        // Create sample DTO
        AlertDTO dto = new AlertDTO();
        dto.symbol = "AAPL";
        dto.alertType = "Price Above";
        dto.thresholdValue = 150.0;

        // Mock saved Alert entity
        Alert saved = new Alert();
        saved.setId(1L);
        saved.setSymbol("AAPL");
        saved.setAlertType("Price Above");
        saved.setThresholdValue(150.0);

        when(alertRepository.save(any(Alert.class))).thenReturn(saved);

        // Call service
        AlertDTO result = alertService.createAlert(dto);

        // Validate results
        assertNotNull(result);
        assertEquals("AAPL", result.symbol);
        assertEquals("Price Above", result.alertType);
        assertEquals(150.0, result.thresholdValue);
    }

    @Test
    public void testUpdateAlertWhenExists() {
        // Existing Alert in DB
        Alert existing = new Alert();
        existing.setId(1L);
        existing.setSymbol("AAPL");
        existing.setAlertType("Price Above");
        existing.setThresholdValue(150.0);

        // New DTO values
        AlertDTO dto = new AlertDTO();
        dto.symbol = "AAPL";
        dto.alertType = "Price Below";
        dto.thresholdValue = 120.0;

        when(alertRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(alertRepository.save(any(Alert.class))).thenReturn(existing);

        AlertDTO result = alertService.updateAlert(1L, dto);

        assertNotNull(result);
        assertEquals("Price Below", result.alertType);
        assertEquals(120.0, result.thresholdValue);
    }

    @Test
    public void testUpdateAlertWhenNotExists() {
        AlertDTO dto = new AlertDTO();
        dto.symbol = "TSLA";
        dto.alertType = "Price Above";
        dto.thresholdValue = 300.0;

        when(alertRepository.findById(99L)).thenReturn(Optional.empty());

        AlertDTO result = alertService.updateAlert(99L, dto);

        assertNull(result);
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

        // Just runs console print, no return
        alertService.evaluateAlerts();

        verify(alertRepository, times(1)).findAll();
    }
}

