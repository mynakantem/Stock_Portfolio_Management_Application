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
        dto.setSymbol("HDFC");
        dto.setAlertType("Price Above");
        dto.setThresholdValue(1920.0);

        Alert saved = new Alert();
        saved.setId(1L);
        saved.setSymbol("HDFC");
        saved.setAlertType("Price Above");
        saved.setThresholdValue(1920.0);

        when(alertRepository.save(any(Alert.class))).thenReturn(saved);

        AlertDTO result = alertService.createAlert(dto);

        assertNotNull(result);
        assertEquals("HDFC", result.getSymbol());
        assertEquals("Price Above", result.getAlertType());
        assertEquals(1920.0, result.getThresholdValue());

    }
}