package com.stockapp.service;

import com.stockapp.dto.GainLossDTO;
import com.stockapp.model.*;
import com.stockapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class GainLossService {
    
    @Autowired
    private GainLossRepository gainLossRepository;
    
    @Autowired
    private PortfolioRepository portfolioRepository;
    
    @Autowired
    private StockPriceRepository stockPriceRepository;
    
    // Calculate gain/loss for a specific portfolio
    public List<GainLossDTO> calculateGainLoss(Long portfolioId) {
        List<GainLossDTO> gainLossList = new ArrayList<>();
        
        // Get portfolio with holdings
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElse(null);
        if (portfolio == null) {
            return gainLossList;
        }
        
        // Calculate gain/loss for each holding
        for (Holding holding : portfolio.getHoldings()) {
            // Get current stock price
        	StockPrice stockPrice = stockPriceRepository.findBySymbol(holding.getSymbol()).orElse(null);
        	if (stockPrice != null) {
                // Calculate gain and percentage
                Double currentPrice = stockPrice.getPrice().doubleValue();
                Double buyPrice = holding.getBuyPrice();
                Integer quantity = holding.getQuantity();
                
                // Formula: gain = (currentPrice - buyPrice) * quantity
                Double gainAmount = (currentPrice - buyPrice) * quantity;
                
                // Formula: percentage = (gain / (buyPrice * quantity)) * 100
                Double gainPercentage = (gainAmount / (buyPrice * quantity)) * 100;
                
                // Create DTO
                GainLossDTO dto = new GainLossDTO();
                dto.setPortfolioName(portfolio.getName());
                dto.setSymbol(holding.getSymbol());
                dto.setQuantity(quantity);
                dto.setBuyPrice(buyPrice);
                dto.setCurrentPrice(currentPrice);
                dto.setGainAmount(gainAmount);
                dto.setGainPercentage(gainPercentage);
                
                gainLossList.add(dto);
                
                // Save to database
                GainLoss gainLoss = new GainLoss();
                gainLoss.setPortfolioName(portfolio.getName());
                gainLoss.setSymbol(holding.getSymbol());
                gainLoss.setQuantity(quantity);
                gainLoss.setBuyPrice(buyPrice);
                gainLoss.setCurrentPrice(currentPrice);
                gainLoss.setGainAmount(gainAmount);
                gainLoss.setGainPercentage(gainPercentage);
                
                gainLossRepository.save(gainLoss);
            }
        }
        
        return gainLossList;
    }
    
    // Get all gain/loss records
    public List<GainLossDTO> getAllGainLoss() {
        List<GainLossDTO> dtoList = new ArrayList<>();
        List<GainLoss> gainLossList = gainLossRepository.findAll();
        
        for (GainLoss gainLoss : gainLossList) {
            GainLossDTO dto = new GainLossDTO();
            dto.setPortfolioName(gainLoss.getPortfolioName());
            dto.setSymbol(gainLoss.getSymbol());
            dto.setQuantity(gainLoss.getQuantity());
            dto.setBuyPrice(gainLoss.getBuyPrice());
            dto.setCurrentPrice(gainLoss.getCurrentPrice());
            dto.setGainAmount(gainLoss.getGainAmount());
            dto.setGainPercentage(gainLoss.getGainPercentage());
            
            dtoList.add(dto);
        }
        
        return dtoList;
    }
    
    // Calculate total portfolio gain/loss
    public GainLossDTO calculateTotalPortfolioGainLoss(Long portfolioId) {
        List<GainLossDTO> stockGainLossList = calculateGainLoss(portfolioId);
        
        Double totalGainAmount = 0.0;
        Double totalInvested = 0.0;
        Double totalCurrentValue = 0.0;
        
        for (GainLossDTO dto : stockGainLossList) {
            totalGainAmount += dto.getGainAmount();
            totalInvested += dto.getBuyPrice() * dto.getQuantity();
            totalCurrentValue += dto.getCurrentPrice() * dto.getQuantity();
        }
        
        Double totalGainPercentage = (totalGainAmount / totalInvested) * 100;
        
        GainLossDTO totalDto = new GainLossDTO();
        totalDto.setPortfolioName("TOTAL");
        totalDto.setSymbol("ALL");
        totalDto.setGainAmount(totalGainAmount);
        totalDto.setGainPercentage(totalGainPercentage);
        
        return totalDto;
    }
}