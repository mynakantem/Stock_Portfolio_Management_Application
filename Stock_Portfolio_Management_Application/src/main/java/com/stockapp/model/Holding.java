package com.stockapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "holdings")
public class Holding {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Stock symbol is required")
    @Pattern(regexp = "^[A-Z]{1,10}$", message = "Symbol must be 1-10 uppercase letters")
    @Column(nullable = false)
    private String symbol;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(nullable = false)
    private Integer quantity;
    
    @NotNull(message = "Buy price is required")
    @DecimalMin(value = "0.01", message = "Buy price must be greater than 0")
    @Column(name = "buy_price", nullable = false)
    private Double buyPrice;
    
    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
    
    // Default Constructor
    public Holding() {
    }
    
    // Constructor with parameters
    public Holding(String symbol, Integer quantity, Double buyPrice) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
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
    
    public Portfolio getPortfolio() {
        return portfolio;
    }
    
    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}