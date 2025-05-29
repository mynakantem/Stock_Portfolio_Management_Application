package com.stockapp.ServiceTest;

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
    public void testEvaluateAlerts() {
        Alert alert = new Alert();
        alert.setId(1L);
        alert.setSymbol("AAPL");
        alert.setAlertType("Price Above");
        alert.setThresholdValue(120.0);

        List<Alert> mockList = Collections.singletonList(alert);

        when(alertRepository.findAll()).thenReturn(mockList);

        alertService.evaluateAlerts();

        verify(alertRepository, times(1)).findAll();
    }
}

