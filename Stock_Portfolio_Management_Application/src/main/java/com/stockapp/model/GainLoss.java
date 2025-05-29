package com.stockapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "gain_loss")
public class GainLoss {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "portfolio_id")
    private Long portfolioId;
    
    @Column(name = "symbol")
    private String symbol;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "buy_price")
    private Double buyPrice;
    
    @Column(name = "current_price")
    private Double currentPrice;
    
    @Column(name = "gain_amount")
    private Double gainAmount;
    
    @Column(name = "gain_percentage")
    private Double gainPercentage;
    
    @Column(name = "date")
    private LocalDate date;
    
    // Constructors
    public GainLoss() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
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