package com.stockapp.dto;

public class GainLossDTO {
    
    private String portfolioName;
    private String symbol;
    private Integer quantity;
    private Double buyPrice;
    private Double currentPrice;
    private Double gainAmount;
    private Double gainPercentage;
    
    // Default Constructor
    public GainLossDTO() {
    }
    
    // Constructor with all fields
    public GainLossDTO(String portfolioName, String symbol, Integer quantity, 
                       Double buyPrice, Double currentPrice, Double gainAmount, 
                       Double gainPercentage) {
        this.portfolioName = portfolioName;
        this.symbol = symbol;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.currentPrice = currentPrice;
        this.gainAmount = gainAmount;
        this.gainPercentage = gainPercentage;
    }
    
    // Getters and Setters
    public String getPortfolioName() {
        return portfolioName;
    }
    
    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
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