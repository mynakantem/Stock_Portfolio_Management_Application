package com.stockapp.service;

import com.stockapp.exception.PortfolioNotFoundException;
import com.stockapp.dto.PortfolioDTO;
import com.stockapp.dto.HoldingDTO;
import com.stockapp.model.Portfolio;
import com.stockapp.model.User;
import com.stockapp.repository.PortfolioRepository;
import com.stockapp.repository.UserRepository;
import com.stockapp.repository.GainLossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortfolioService {
    
    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);
    
    @Autowired
    private PortfolioRepository portfolioRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GainLossRepository gainLossRepository;
    
    @Autowired
    private HoldingService holdingService;
    
    // Create a new portfolio
    public PortfolioDTO createPortfolio(PortfolioDTO portfolioDTO, Long userId) {
        logger.info("Creating portfolio for user: {}", userId);
        
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            logger.warn("User not found with id: {}", userId);
            return null;
        }
        
        Portfolio portfolio = new Portfolio();
        portfolio.setName(portfolioDTO.getName());
        portfolio.setUser(user);
        
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        logger.info("Portfolio created successfully with id: {}", savedPortfolio.getId());
        
        return convertToDTO(savedPortfolio);
    }
    
    // Get all portfolios for a user
    public List<PortfolioDTO> getAllPortfoliosForUser(Long userId) {
        logger.info("Fetching all portfolios for user: {}", userId);
        
        List<Portfolio> portfolios = portfolioRepository.findByUserId(userId);
        
        return portfolios.stream()
                .map(portfolio -> {
                    PortfolioDTO dto = convertToDTO(portfolio);
                    
                    // Get holdings with gain/loss data
                    List<HoldingDTO> holdingsWithData = holdingService.getAllHoldingsForPortfolio(portfolio.getId());
                    dto.setHoldings(holdingsWithData);
                    
                    // Get total gain/loss from GainLoss repository
                    Double totalGain = gainLossRepository.getTotalPortfolioGain(portfolio.getId(), LocalDate.now());
                    if (totalGain != null) {
                        dto.setTotalGainAmount(totalGain);
                    }
                    
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    // Get a single portfolio by ID
    public PortfolioDTO getPortfolioById(Long portfolioId) {
        logger.info("Fetching portfolio with id: {}", portfolioId);
        
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new PortfolioNotFoundException(portfolioId));
        
        PortfolioDTO dto = convertToDTO(portfolio);
        
        // Get holdings with gain/loss data
        List<HoldingDTO> holdingsWithData = holdingService.getAllHoldingsForPortfolio(portfolioId);
        dto.setHoldings(holdingsWithData);
        
        // Get total gain/loss from GainLoss repository
        Double totalGain = gainLossRepository.getTotalPortfolioGain(portfolioId, LocalDate.now());
        if (totalGain != null) {
            dto.setTotalGainAmount(totalGain);
        }
        
        return dto;
    }
    
    // Update portfolio name
    public PortfolioDTO updatePortfolio(Long portfolioId, String newName) {
        logger.info("Updating portfolio: {} with new name: {}", portfolioId, newName);
        
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElse(null);
        
        if (portfolio == null) {
            logger.warn("Portfolio not found with id: {}", portfolioId);
            return null;
        }
        
        portfolio.setName(newName);
        Portfolio updatedPortfolio = portfolioRepository.save(portfolio);
        logger.info("Portfolio updated successfully");
        
        return convertToDTO(updatedPortfolio);
    }
    
    // Delete a portfolio
    public boolean deletePortfolio(Long portfolioId) {
        logger.info("Deleting portfolio: {}", portfolioId);
        
        if (!portfolioRepository.existsById(portfolioId)) {
            logger.warn("Portfolio not found with id: {}", portfolioId);
            return false;
        }
        
        portfolioRepository.deleteById(portfolioId);
        logger.info("Portfolio deleted successfully");
        return true;
    }
    
    // Check if user owns the portfolio
    public boolean isUserOwnerOfPortfolio(Long userId, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElse(null);
        
        if (portfolio == null) {
            return false;
        }
        
        return portfolio.getUser().getId().equals(userId);
    }
    
    // Helper method to convert Portfolio to PortfolioDTO
    private PortfolioDTO convertToDTO(Portfolio portfolio) {
        PortfolioDTO dto = new PortfolioDTO();
        dto.setId(portfolio.getId());
        dto.setName(portfolio.getName());
        dto.setUserId(portfolio.getUser().getId());
        return dto;
    }
}