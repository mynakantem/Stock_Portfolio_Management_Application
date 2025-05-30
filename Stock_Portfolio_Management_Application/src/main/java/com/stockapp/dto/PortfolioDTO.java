package com.stockapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO to represent portfolio details, including holdings and total gain.
 */
@Data
@NoArgsConstructor
public class PortfolioDTO {

    private Long id;

    private Long userId;

    @NotBlank(message = "Portfolio name is required")
    @Size(min = 3, max = 50, message = "Portfolio name must be between 3 and 50 characters")
    private String name;

    // List of holdings in this portfolio (populated when requested)
    private List<HoldingDTO> holdings;

    // Optional: Calculated total gain amount for the current day
    private Double totalGainAmount;
}
