package com.stockapp.service;

import com.stockapp.model.*;

import com.stockapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GainLossService {
    
    private static final Logger log = LoggerFactory.getLogger(GainLossService.class);
    
    @Autowired
    private GainLossRepository gainLossRepository;
    
    @Autowired
    private PortfolioRepository portfolioRepository;
    
    @Autowired
    private StockPriceRepository stockPriceRepository;
    
    // Calculate per-stock gain/loss
    public List<GainLoss> calculateStockGainLoss(Long portfolioId) {
        log.info("Calculating stock gain/loss for portfolio: {}", portfolioId);
        List<GainLoss> gainLossList = new ArrayList<>();
        
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElse(null);
        if (portfolio == null) {
            log.warn("Portfolio not found: {}", portfolioId);
            return gainLossList;
        }
        
        for (Holding holding : portfolio.getHoldings()) {
            StockPrice stockPrice = stockPriceRepository.findByStockSymbol(holding.getSymbol()).orElse(null);
            if (stockPrice != null) {
            	Double currentPrice = stockPrice.getPrice();
                Double buyPrice = holding.getBuyPrice();
                Integer quantity = holding.getQuantity();
                
                Double gainAmount = (currentPrice - buyPrice) * quantity;
                Double gainPercentage = (gainAmount / (buyPrice * quantity)) * 100;
                
                log.info("Stock: {} Gain: ${}", holding.getSymbol(), gainAmount);
                
                GainLoss gainLoss = new GainLoss();
                gainLoss.setPortfolioId(portfolioId);
                gainLoss.setSymbol(holding.getSymbol());
                gainLoss.setQuantity(quantity);
                gainLoss.setBuyPrice(buyPrice);
                gainLoss.setCurrentPrice(currentPrice);
                gainLoss.setGainAmount(gainAmount);
                gainLoss.setGainPercentage(gainPercentage);
                gainLoss.setDate(LocalDate.now());
                
                gainLossList.add(gainLoss);
                gainLossRepository.save(gainLoss);
            }
        }
        
        return gainLossList;
    }
    
    // Calculate total portfolio gain/loss
    public Double calculateTotalPortfolioGainLoss(Long portfolioId) {
        log.info("Calculating total portfolio gain/loss for: {}", portfolioId);
        calculateStockGainLoss(portfolioId);
        return gainLossRepository.getTotalPortfolioGain(portfolioId, LocalDate.now());
    }
    
    // Get daily portfolio gain/loss tracking
    public Double getDailyPortfolioGainLoss(Long portfolioId, LocalDate date) {
        log.info("Getting daily gain/loss for portfolio: {} on: {}", portfolioId, date);
        return gainLossRepository.getTotalPortfolioGain(portfolioId, date);
    }
}

