package com.stockapp.controller;

import com.stockapp.dto.HoldingDTO;
import com.stockapp.service.HoldingService;
import com.stockapp.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/holdings")
public class HoldingController {
    
    private static final Logger logger = LoggerFactory.getLogger(HoldingController.class);
    
    @Autowired
    private HoldingService holdingService;
    
    @Autowired
    private PortfolioService portfolioService;
    
    // POST /api/holdings - Add a new holding
    @PostMapping
    public ResponseEntity<HoldingDTO> addHolding(@Valid @RequestBody HoldingDTO holdingDTO) {
        logger.info("POST request to add holding: {} to portfolio: {}", holdingDTO.getSymbol(), holdingDTO.getPortfolioId());
        
        // TODO: Get user ID from JWT/Security context
        Long userId = 1L; // This should come from authentication
        
        // Check if user owns the portfolio
        if (!portfolioService.isUserOwnerOfPortfolio(userId, holdingDTO.getPortfolioId())) {
            logger.warn("User {} does not own portfolio {}", userId, holdingDTO.getPortfolioId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        HoldingDTO createdHolding = holdingService.addHolding(holdingDTO);
        
        if (createdHolding == null) {
            logger.warn("Failed to add holding");
            return ResponseEntity.badRequest().build();
        }
        
        logger.info("Holding added successfully with id: {}", createdHolding.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHolding);
    }
    
    // PUT /api/holdings/{id} - Update a holding
    @PutMapping("/{id}")
    public ResponseEntity<HoldingDTO> updateHolding(@PathVariable Long id, @Valid @RequestBody HoldingDTO holdingDTO) {
        logger.info("PUT request to update holding: {}", id);
        
        // First, get the existing holding to check ownership
        HoldingDTO existingHolding = holdingService.getHoldingById(id);
        
        if (existingHolding == null) {
            logger.warn("Holding not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        
        // TODO: Get user ID from JWT/Security context
        Long userId = 1L; // This should come from authentication
        
        // Check if user owns the portfolio
        if (!portfolioService.isUserOwnerOfPortfolio(userId, existingHolding.getPortfolioId())) {
            logger.warn("User {} does not own the portfolio containing holding {}", userId, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        HoldingDTO updatedHolding = holdingService.updateHolding(id, holdingDTO);
        
        if (updatedHolding == null) {
            logger.warn("Failed to update holding: {}", id);
            return ResponseEntity.notFound().build();
        }
        
        logger.info("Holding {} updated successfully", id);
        return ResponseEntity.ok(updatedHolding);
    }
    
    // DELETE /api/holdings/{id} - Delete a holding
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHolding(@PathVariable Long id) {
        logger.info("DELETE request for holding: {}", id);
        
        // First, get the holding to check ownership
        HoldingDTO holding = holdingService.getHoldingById(id);
        
        if (holding == null) {
            logger.warn("Holding not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        
        // TODO: Get user ID from JWT/Security context
        Long userId = 1L; // This should come from authentication
        
        // Check if user owns the portfolio
        if (!portfolioService.isUserOwnerOfPortfolio(userId, holding.getPortfolioId())) {
            logger.warn("User {} does not own the portfolio containing holding {}", userId, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        boolean deleted = holdingService.deleteHolding(id);
        
        if (!deleted) {
            logger.warn("Failed to delete holding: {}", id);
            return ResponseEntity.notFound().build();
        }
        
        logger.info("Holding {} deleted successfully", id);
        return ResponseEntity.ok("Holding deleted successfully");
    }
    
    // GET /api/holdings/{id} - Get a specific holding
    @GetMapping("/{id}")
    public ResponseEntity<HoldingDTO> getHoldingById(@PathVariable Long id) {
        logger.info("GET request to fetch holding: {}", id);
        
        HoldingDTO holding = holdingService.getHoldingById(id);
        
        if (holding == null) {
            logger.warn("Holding not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        
        // TODO: Get user ID from JWT/Security context
        Long userId = 1L; // This should come from authentication
        
        // Check if user owns the portfolio
        if (!portfolioService.isUserOwnerOfPortfolio(userId, holding.getPortfolioId())) {
            logger.warn("User {} does not own the portfolio containing holding {}", userId, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok(holding);
    }
}