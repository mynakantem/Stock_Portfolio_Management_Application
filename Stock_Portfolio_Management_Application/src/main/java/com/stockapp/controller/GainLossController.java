package com.stockapp.controller;

import com.stockapp.dto.GainLossDTO;
import com.stockapp.service.GainLossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/gainloss")
public class GainLossController {
    
    @Autowired
    private GainLossService gainLossService;
    
    // Calculate gain/loss for a portfolio
    @GetMapping("/calculate/{portfolioId}")
    public ResponseEntity<List<GainLossDTO>> calculateGainLoss(@PathVariable Long portfolioId) {
        List<GainLossDTO> gainLossList = gainLossService.calculateGainLoss(portfolioId);
        return new ResponseEntity<>(gainLossList, HttpStatus.OK);
    }
    
    // Get all gain/loss records
    @GetMapping("/all")
    public ResponseEntity<List<GainLossDTO>> getAllGainLoss() {
        List<GainLossDTO> gainLossList = gainLossService.getAllGainLoss();
        return new ResponseEntity<>(gainLossList, HttpStatus.OK);
    }
    
    // Get total portfolio gain/loss
    @GetMapping("/total/{portfolioId}")
    public ResponseEntity<GainLossDTO> getTotalPortfolioGainLoss(@PathVariable Long portfolioId) {
        GainLossDTO totalGainLoss = gainLossService.calculateTotalPortfolioGainLoss(portfolioId);
        return new ResponseEntity<>(totalGainLoss, HttpStatus.OK);
    }
}