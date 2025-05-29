package com.stockapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "gain_loss")
public class GainLoss {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "portfolio_id", nullable = false)
    private Long portfolioId;
    
    @Column(nullable = false)
    private String symbol;
    
    @Column(name = "gain_amount", nullable = false)
    private Double gainAmount;
    
    @Column(name = "gain_percentage", nullable = false)
    private Double gainPercentage;
    
    @Column(name = "daily_change", nullable = false)
    private Double dailyChange;
    
    @Column(nullable = false)
    private LocalDate date;
    
    // Default Constructor
    public GainLoss() {
    }
    
    // Constructor with parameters
    public GainLoss(Long portfolioId, String symbol, Double gainAmount, Double gainPercentage, Double dailyChange, LocalDate date) {
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