package com.stockapp.controller;

import com.stockapp.dto.PortfolioDTO;
import com.stockapp.dto.HoldingDTO;
import com.stockapp.service.PortfolioService;
import com.stockapp.service.HoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    
    private static final Logger logger = LoggerFactory.getLogger(PortfolioController.class);
    
    @Autowired
    private PortfolioService portfolioService;
    
    @Autowired
    private HoldingService holdingService;
    
    // GET /api/portfolios - Get all portfolios for logged in user
    @GetMapping
    public ResponseEntity<List<PortfolioDTO>> getAllPortfolios() {
        logger.info("GET request to fetch all portfolios");
        
        // TODO: Get user ID from JWT/Security context
        Long userId = 1L; // This should come from authentication
        
        List<PortfolioDTO> portfolios = portfolioService.getAllPortfoliosForUser(userId);
        logger.info("Returning {} portfolios for user: {}", portfolios.size(), userId);
        
        return ResponseEntity.ok(portfolios);
    }
    
    // POST /api/portfolios - Create a new portfolio
    @PostMapping
    public ResponseEntity<PortfolioDTO> createPortfolio(@Valid @RequestBody PortfolioDTO portfolioDTO) {
        logger.info("POST request to create portfolio: {}", portfolioDTO.getName());
        
        // TODO: Get user ID from JWT/Security context
        Long userId = 1L; // This should come from authentication
        
        PortfolioDTO createdPortfolio = portfolioService.createPortfolio(portfolioDTO, userId);
        
        if (createdPortfolio == null) {
            logger.warn("Failed to create portfolio");
            return ResponseEntity.badRequest().build();
        }
        
        logger.info("Portfolio created successfully with id: {}", createdPortfolio.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPortfolio);
    }
    
    // GET /api/portfolios/{id}/holdings - Get all holdings for a portfolio
    @GetMapping("/{id}/holdings")
    public ResponseEntity<List<HoldingDTO>> getHoldingsByPortfolio(@PathVariable Long id) {
        logger.info("GET request to fetch holdings for portfolio: {}", id);
        
        // TODO: Verify user owns this portfolio
        Long userId = 1L; // This should come from authentication
        
        if (!portfolioService.isUserOwnerOfPortfolio(userId, id)) {
            logger.warn("User {} does not own portfolio {}", userId, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        List<HoldingDTO> holdings = holdingService.getAllHoldingsForPortfolio(id);
        logger.info("Returning {} holdings for portfolio: {}", holdings.size(), id);
        
        return ResponseEntity.ok(holdings);
    }
    
    // GET /api/portfolios/{id} - Get a specific portfolio
    @GetMapping("/{id}")
    public ResponseEntity<PortfolioDTO> getPortfolioById(@PathVariable Long id) {
        logger.info("GET request to fetch portfolio: {}", id);
        
        // TODO: Verify user owns this portfolio
        Long userId = 1L; // This should come from authentication
        
        if (!portfolioService.isUserOwnerOfPortfolio(userId, id)) {
            logger.warn("User {} does not own portfolio {}", userId, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        PortfolioDTO portfolio = portfolioService.getPortfolioById(id);
        
        if (portfolio == null) {
            logger.warn("Portfolio not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(portfolio);
    }
    
    // PUT /api/portfolios/{id} - Update portfolio name
    @PutMapping("/{id}")
    public ResponseEntity<PortfolioDTO> updatePortfolio(@PathVariable Long id, @Valid @RequestBody PortfolioDTO portfolioDTO) {
        logger.info("PUT request to update portfolio: {}", id);
        
        // TODO: Verify user owns this portfolio
        Long userId = 1L; // This should come from authentication
        
        if (!portfolioService.isUserOwnerOfPortfolio(userId, id)) {
            logger.warn("User {} does not own portfolio {}", userId, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        PortfolioDTO updatedPortfolio = portfolioService.updatePortfolio(id, portfolioDTO.getName());
        
        if (updatedPortfolio == null) {
            logger.warn("Failed to update portfolio: {}", id);
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(updatedPortfolio);
    }
    
    // DELETE /api/portfolios/{id} - Delete a portfolio
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePortfolio(@PathVariable Long id) {
        logger.info("DELETE request for portfolio: {}", id);
        
        // TODO: Verify user owns this portfolio
        Long userId = 1L; // This should come from authentication
        
        if (!portfolioService.isUserOwnerOfPortfolio(userId, id)) {
            logger.warn("User {} does not own portfolio {}", userId, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        boolean deleted = portfolioService.deletePortfolio(id);
        
        if (!deleted) {
            logger.warn("Failed to delete portfolio: {}", id);
            return ResponseEntity.notFound().build();
        }
        
        logger.info("Portfolio {} deleted successfully", id);
        return ResponseEntity.ok("Portfolio deleted successfully");
    }
}