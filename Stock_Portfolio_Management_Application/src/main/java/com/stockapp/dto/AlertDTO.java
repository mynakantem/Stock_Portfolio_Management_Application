package com.stockapp.dto;

import jakarta.validation.constraints.*;

public class AlertDTO {
	public Long id;
	@NotBlank(message = "Symbol is required")
	public String symbol;
	@NotBlank(message = "Alert type is required")
	@Pattern(regexp = "^(Price Above | Price Below)$", message = "Alert type must be Price Above or Price Below")
	public String alertType;
	@Min(value = 1, message = "Threshold must be greater than 0")
	public double thresholdValue;
}


