package com.stockapp.dto;

import java.time.LocalDate;

public class GainLossDTO {
    
    private Long id;
    private Long portfolioId;
    private String symbol;
    private Double gainAmount;
    private Double gainPercentage;
    private Double dailyChange;
    private LocalDate date;
    
    // Default Constructor
    public GainLossDTO() {
    }
    
    // Constructor with parameters
    public GainLossDTO(Long id, Long portfolioId, String symbol, Double gainAmount, Double gainPercentage, Double dailyChange, LocalDate date) {
        this.id = id;
        this.portfolioId = portfolioId;
        this.symbol = symbol;
        this.gainAmount = gainAmount;
        this.gainPercentage = gainPercentage;
        this.dailyChange = dailyChange;
        this.date = date;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getPortfolioId() {
        return portfolioId;
    }
    
    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
    
    public Double getDailyChange() {
        return dailyChange;
    }
    
    public void setDailyChange(Double dailyChange) {
        this.dailyChange = dailyChange;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
}