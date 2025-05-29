package com.stockapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used to send or receive holding data from the client.
 */
@Data
@NoArgsConstructor
public class HoldingDTO {

    private Long id;

    @NotBlank(message = "Stock symbol is required")
    @Pattern(regexp = "^[A-Z]{1,10}$", message = "Symbol must be 1-10 uppercase letters")
    private String symbol;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Buy price is required")
    @DecimalMin(value = "0.01", message = "Buy price must be greater than 0")
    private Double buyPrice;

    private Long portfolioId;

    // Optional values returned for display purposes
    private Double currentPrice;
    private Double gainAmount;
    private Double gainPercentage;
}
