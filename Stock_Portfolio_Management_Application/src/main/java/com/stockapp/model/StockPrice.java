package com.stockapp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_prices")
public class StockPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String symbol;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    public StockPrice() {
    }

    public StockPrice(Long id, String symbol, BigDecimal price, LocalDateTime lastUpdated) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    // --- Builder ---
    public static class Builder {
        private String symbol;
        private BigDecimal price;
        private LocalDateTime lastUpdated;

        public Builder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder lastUpdated(LocalDateTime lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public StockPrice build() {
            return new StockPrice(null, symbol, price, lastUpdated);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}

