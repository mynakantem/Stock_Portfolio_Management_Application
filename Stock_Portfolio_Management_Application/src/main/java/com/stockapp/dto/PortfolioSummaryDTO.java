package com.stockapp.dto;

import java.time.LocalDate;
import java.util.List;

//DTO used to return summary details of all portfolios for a user.

public class PortfolioSummaryDTO {
    
    private Long userId;
    private LocalDate summaryDate;
    private Integer totalPortfolios;
    private Integer totalHoldings;
    private List<PortfolioDTO> portfolios;
    
    // Default Constructor
    public PortfolioSummaryDTO() {
        this.summaryDate = LocalDate.now();
    }
    
    // Getters and Setters
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public LocalDate getSummaryDate() {
        return summaryDate;
    }
    
    public void setSummaryDate(LocalDate summaryDate) {
        this.summaryDate = summaryDate;
    }
    
    public Integer getTotalPortfolios() {
        return totalPortfolios;
    }
    
    public void setTotalPortfolios(Integer totalPortfolios) {
        this.totalPortfolios = totalPortfolios;
    }
    
    public Integer getTotalHoldings() {
        return totalHoldings;
    }
    
    public void setTotalHoldings(Integer totalHoldings) {
        this.totalHoldings = totalHoldings;
    }
    
    public List<PortfolioDTO> getPortfolios() {
        return portfolios;
    }
    
    public void setPortfolios(List<PortfolioDTO> portfolios) {
        this.portfolios = portfolios;
    }
}