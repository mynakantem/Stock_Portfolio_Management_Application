package com.stockapp.dto;

import jakarta.validation.constraints.*;

public class AlertDTO {
	private Long id;
	@NotBlank(message = "Symbol is required")
	 @Pattern(regexp = "^[A-Z]{1,10}$", message = "Symbol must be 1-10 uppercase letters")
	private String symbol;
	@NotBlank(message = "Alert type is required")
	@Pattern(regexp = "^(Price Above | Price Below)$", message = "Alert type must be Price Above or Price Below")
	private String alertType;
	@Min(value = 1, message = "Threshold must be greater than 0")
	private double thresholdValue;
	
	// Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public double getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(double thresholdValue) {
        this.thresholdValue = thresholdValue;
    }
}

