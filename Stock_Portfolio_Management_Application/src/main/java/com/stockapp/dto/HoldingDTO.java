package com.stockapp.dto;

public class HoldingDTO {
    
    private Long id;
    private String symbol;
    private Integer quantity;
    private Double buyPrice;
    private Long portfolioId;
    
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
}
