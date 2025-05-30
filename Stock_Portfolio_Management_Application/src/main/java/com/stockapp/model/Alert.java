package com.stockapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "alerts")
public class Alert {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	  @Column(nullable = false, length = 10)
	 private String symbol;
	  @Column(nullable = false)
	 private String alertType;
	  @Column(nullable = false)
	 private double thresholdValue;
	  
	  // default constructor
	  public Alert() {	  
	  }
	
	  public Alert(Long id, String symbol, String alertType, double thresholdValue) {
	        this.id = id;
	        this.symbol = symbol;
	        this.alertType = alertType;
	        this.thresholdValue = thresholdValue;
	    }
	 
	 //getter
	    public Long getId() {
	        return id;
	    }
	    public String getSymbol() {
	        return symbol;
	    }
	    public String getAlertType() {
	        return alertType;
	    }
	    public double getThresholdValue() {
	        return thresholdValue;
	    }
	    
	    //setter
	    public void setId(Long id) {
	        this.id = id;
	    }
	    public void setSymbol(String symbol) {
	        this.symbol = symbol;
	    }
	    public void setAlertType(String alertType) {
	        this.alertType = alertType;
	    }
	    public void setThresholdValue(double thresholdValue) {
	        this.thresholdValue = thresholdValue;
	    }
	}