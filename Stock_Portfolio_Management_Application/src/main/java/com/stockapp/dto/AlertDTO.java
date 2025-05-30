package com.stockapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AlertDTO {
    private Long id;

    @NotBlank(message = "Symbol is required")
    @Pattern(regexp = "^[A-Z]{1,10}$", message = "Symbol must be 1-10 uppercase letters")
    private String symbol;

    @NotBlank(message = "Alert type is required")
    @Pattern(regexp = "^(Price Above|Price Below)$", message = "Alert type must be Price Above or Price Below")
    private String alertType;

    @Min(value = 1, message = "Threshold must be greater than 0")
    private double thresholdValue;
}
