package com.stockapp.dto;

import jakarta.validation.constraints.*;

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
    private Double currentPrice;
    private Double gainAmount;
    private Double gainPercentage;
    
    // Default Constructor
    public HoldingDTO() {
    }
    
    // Constructor with parameters
    public HoldingDTO(Long id, String symbol, Integer quantity, Double buyPrice, Long portfolioId) {
        this.id = id;
        this.symbol = symbol;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.portfolioId = portfolioId;
    }
    
    // Getters and Setters
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
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public Double getBuyPrice() {
        return buyPrice;
    }
    
    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }
    
    public Long getPortfolioId() {
        return portfolioId;
    }
    
    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }
    
    public Double getCurrentPrice() {
        return currentPrice;
    }
    
    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }
    
    public Double getGainAmount() {
        return gainAmount;
    }
    
    public void setGainAmount(Double gainAmount) {
        this.gainAmount = gainAmount;
    }
    
    public Double getGainPercentage() {
        return gainPercentage;
    }
    
    public void setGainPercentage(Double gainPercentage) {
        this.gainPercentage = gainPercentage;
    }
}
