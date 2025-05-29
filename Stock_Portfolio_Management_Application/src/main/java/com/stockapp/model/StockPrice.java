package com.stockapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//Entity to store cached stock prices fetched from API.
 
	@Data
	@NoArgsConstructor
	@Entity
	@Table(name = "stock_prices")
	public class StockPrice {

	    @Id
	    private String stockSymbol; // e.g., TCS, INFY

	    private double price;

	    private LocalDateTime lastUpdated; // when this price was fetched
	}
