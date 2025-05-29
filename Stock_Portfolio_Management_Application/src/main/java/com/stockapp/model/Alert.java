package com.stockapp.model;

import jakarta.persistence.*;

@Entity
public class Alert {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 public Long id;
	 
	 public String symbol;
	 public String alertType;
	 public double thresholdValue;
	 
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
