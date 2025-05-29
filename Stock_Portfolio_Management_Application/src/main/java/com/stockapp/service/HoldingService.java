package com.stockapp.service;

import com.stockapp.exception.PortfolioNotFoundException;
import com.stockapp.dto.HoldingDTO;
import com.stockapp.model.Holding;
import com.stockapp.model.Portfolio;
import com.stockapp.model.StockPrice;
import com.stockapp.model.GainLoss;
import com.stockapp.repository.HoldingRepository;
import com.stockapp.repository.PortfolioRepository;
import com.stockapp.repository.StockPriceRepository;
import com.stockapp.repository.GainLossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoldingService {
    
    private static final Logger logger = LoggerFactory.getLogger(HoldingService.class);
    
    @Autowired
    private HoldingRepository holdingRepository;
    
    @Autowired
    private PortfolioRepository portfolioRepository;
    
    @Autowired
    private StockPriceRepository stockPriceRepository;
    
    @Autowired
    private GainLossRepository gainLossRepository;
    
    // Add a new holding to a portfolio
    public HoldingDTO addHolding(HoldingDTO holdingDTO) {
        logger.info("Adding holding for symbol: {} to portfolio: {}", holdingDTO.getSymbol(), holdingDTO.getPortfolioId());
        
        // Check if portfolio exists
        Portfolio portfolio = portfolioRepository.findById(holdingDTO.getPortfolioId())
                .orElseThrow(() -> new PortfolioNotFoundException(holdingDTO.getPortfolioId()));
        
        // Create new holding
        Holding holding = new Holding();
        holding.setSymbol(holdingDTO.getSymbol().toUpperCase());
        holding.setQuantity(holdingDTO.getQuantity());
        holding.setBuyPrice(holdingDTO.getBuyPrice());
        holding.setPortfolio(portfolio);
        
        // Save to database
        Holding savedHolding = holdingRepository.save(holding);
        logger.info("Holding added successfully with id: {}", savedHolding.getId());
        
        return convertToDTO(savedHolding);
    }
    
    // Get all holdings for a portfolio
    public List<HoldingDTO> getAllHoldingsForPortfolio(Long portfolioId) {
        logger.info("Fetching all holdings for portfolio: {}", portfolioId);
        
        List<Holding> holdings = holdingRepository.findByPortfolioId(portfolioId);
        
        return holdings.stream()
                .map(holding -> {
                    HoldingDTO dto = convertToDTO(holding);
                    
                    // Get current price from StockPrice table
                    StockPrice stockPrice = stockPriceRepository.findByStockSymbol(holding.getSymbol()).orElse(null);
                    if (stockPrice != null) {
                        dto.setCurrentPrice(stockPrice.getPrice());
                    }
                    
                    // Get gain/loss data from GainLoss table
                    List<GainLoss> gainLossList = gainLossRepository.findByPortfolioIdAndDate(portfolioId, LocalDate.now());
                    if (gainLossList != null) {
                        gainLossList.stream()
                            .filter(gl -> gl.getSymbol().equals(holding.getSymbol()))
                            .findFirst()
                            .ifPresent(gl -> {
                                dto.setGainAmount(gl.getGainAmount());
                                dto.setGainPercentage(gl.getGainPercentage());
                            });
                    }
                    
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    // Get a single holding by ID
    public HoldingDTO getHoldingById(Long holdingId) {
        logger.info("Fetching holding with id: {}", holdingId);
        
        Holding holding = holdingRepository.findById(holdingId).orElse(null);
        
        if (holding == null) {
            logger.warn("Holding not found with id: {}", holdingId);
            return null;
        }
        
        HoldingDTO dto = convertToDTO(holding);
        
        // Get current price
        StockPrice stockPrice = stockPriceRepository.findByStockSymbol(holding.getSymbol()).orElse(null);
        if (stockPrice != null) {
            dto.setCurrentPrice(stockPrice.getPrice());
        }
        
        // Get gain/loss data
        List<GainLoss> gainLossList = gainLossRepository.findByPortfolioIdAndDate(
            holding.getPortfolio().getId(), LocalDate.now()
        );
        if (gainLossList != null) {
            gainLossList.stream()
                .filter(gl -> gl.getSymbol().equals(holding.getSymbol()))
                .findFirst()
                .ifPresent(gl -> {
                    dto.setGainAmount(gl.getGainAmount());
                    dto.setGainPercentage(gl.getGainPercentage());
                });
        }
        
        return dto;
    }
    
    // Update a holding
    public HoldingDTO updateHolding(Long holdingId, HoldingDTO holdingDTO) {
        logger.info("Updating holding: {}", holdingId);
        
        Holding holding = holdingRepository.findById(holdingId).orElse(null);
        
        if (holding == null) {
            logger.warn("Holding not found with id: {}", holdingId);
            return null;
        }
        
        // Update fields
        holding.setQuantity(holdingDTO.getQuantity());
        holding.setBuyPrice(holdingDTO.getBuyPrice());
        
        // Save updated holding
        Holding updatedHolding = holdingRepository.save(holding);
        logger.info("Holding updated successfully");
        
        return convertToDTO(updatedHolding);
    }
    
    // Delete a holding
    public boolean deleteHolding(Long holdingId) {
        logger.info("Deleting holding: {}", holdingId);
        
        if (!holdingRepository.existsById(holdingId)) {
            logger.warn("Holding not found with id: {}", holdingId);
            return false;
        }
        
        holdingRepository.deleteById(holdingId);
        logger.info("Holding deleted successfully");
        return true;
    }
    
    // Helper method to convert Holding to HoldingDTO
    private HoldingDTO convertToDTO(Holding holding) {
        HoldingDTO dto = new HoldingDTO();
        dto.setId(holding.getId());
        dto.setSymbol(holding.getSymbol());
        dto.setQuantity(holding.getQuantity());
        dto.setBuyPrice(holding.getBuyPrice());
        dto.setPortfolioId(holding.getPortfolio().getId());
        return dto;
    }
}