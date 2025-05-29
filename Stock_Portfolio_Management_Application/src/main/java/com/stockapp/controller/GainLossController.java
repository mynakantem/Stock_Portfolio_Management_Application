package com.stockapp.controller;

import com.stockapp.dto.GainLossDTO;
import com.stockapp.service.GainLossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/gain-loss")
public class GainLossController {
    
    private static final Logger logger = LoggerFactory.getLogger(GainLossController.class);
    
    @Autowired
    private GainLossService gainLossService;
    
    // GET /api/gain-loss/portfolio/{portfolioId} - Get per-stock gain/loss (absolute and %)
    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<List<GainLossDTO>> getPerStockGainLoss(@PathVariable Long portfolioId) {
        logger.info("GET request to fetch per-stock gain/loss for portfolio: {}", portfolioId);
        
        List<GainLossDTO> gainLossList = gainLossService.getGainLossForPortfolio(portfolioId, LocalDate.now());
        
        return ResponseEntity.ok(gainLossList);
    }
    
    // GET /api/gain-loss/portfolio/{portfolioId}/total - Get total portfolio gain/loss
    @GetMapping("/portfolio/{portfolioId}/total")
    public ResponseEntity<Double> getTotalPortfolioGain(@PathVariable Long portfolioId) {
        logger.info("GET request to fetch total portfolio gain for portfolio: {}", portfolioId);
        
        Double totalGain = gainLossService.getTotalPortfolioGain(portfolioId, LocalDate.now());
        
        return ResponseEntity.ok(totalGain);
    }
<<<<<<< Updated upstream
    
    // Daily portfolio gain/loss tracking
    @GetMapping("/daily/{portfolioId}")
    public Double getDailyPortfolioGainLoss(@PathVariable Long portfolioId, 
                                           @RequestParam String date) {
        log.info("Getting daily gain/loss for portfolio: {} on date: {}", portfolioId, date);
        return gainLossService.getDailyPortfolioGainLoss(portfolioId, LocalDate.parse(date));
    }
=======
>>>>>>> Stashed changes
}