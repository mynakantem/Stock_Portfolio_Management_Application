package com.stockapp.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stock_prices")
public class StockPrice {
    @Id
    private String stockSymbol;
    private double price;
    private LocalDateTime lastUpdated;
}
