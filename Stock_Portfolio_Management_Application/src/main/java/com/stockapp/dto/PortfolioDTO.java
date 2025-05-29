package com.stockapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class PortfolioDTO {
    
    private Long id;
    
    @NotBlank(message = "Portfolio name is required")
    @Size(min = 3, max = 50, message = "Portfolio name must be between 3 and 50 characters")
    private String name;
    
    private Long userId;
    private List<HoldingDTO> holdings;
    private Double totalGainAmount;
    private Double totalGainPercentage;
    
    // Default Constructor
    public PortfolioDTO() {
    }
    
    // Constructor with parameters
    public PortfolioDTO(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public List<HoldingDTO> getHoldings() {
        return holdings;
    }
    
    public void setHoldings(List<HoldingDTO> holdings) {
        this.holdings = holdings;
    }
    
    public Double getTotalGainAmount() {
        return totalGainAmount;
    }
    
    public void setTotalGainAmount(Double totalGainAmount) {
        this.totalGainAmount = totalGainAmount;
    }
    
    public Double getTotalGainPercentage() {
        return totalGainPercentage;
    }
    
    public void setTotalGainPercentage(Double totalGainPercentage) {
        this.totalGainPercentage = totalGainPercentage;
    }
}