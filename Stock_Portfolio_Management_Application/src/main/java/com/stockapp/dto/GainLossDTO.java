package com.stockapp.dto;

import java.time.LocalDate;

public class GainLossDTO {
    
    private Long portfolioId;
    private String symbol;
    private Integer quantity;
    private Double buyPrice;
    private Double currentPrice;
    private Double gainAmount;
    private Double gainPercentage;
    private LocalDate date;
    
    public GainLossDTO() {}
    
    // Getters and Setters
    public Long getPortfolioId() { return portfolioId; }
    public void setPortfolioId(Long portfolioId) { this.portfolioId = portfolioId; }
    
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public Double getBuyPrice() { return buyPrice; }
    public void setBuyPrice(Double buyPrice) { this.buyPrice = buyPrice; }
    
    public Double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(Double currentPrice) { this.currentPrice = currentPrice; }
    
    public Double getGainAmount() { return gainAmount; }
    public void setGainAmount(Double gainAmount) { this.gainAmount = gainAmount; }
    
    public Double getGainPercentage() { return gainPercentage; }
    public void setGainPercentage(Double gainPercentage) { this.gainPercentage = gainPercentage; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}