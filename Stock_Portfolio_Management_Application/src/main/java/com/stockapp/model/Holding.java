package com.stockapp.model;

public class Holding {
<<<<<<< Updated upstream

}
=======
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String symbol;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(name = "buy_price", nullable = false)
    private Double buyPrice;
    
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
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
>>>>>>> Stashed changes
