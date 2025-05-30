package com.stockapp.controller;

import com.stockapp.dto.GainLossDTO;
import com.stockapp.service.GainLossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/gainloss")
public class GainLossController {

    @Autowired
    private GainLossService gainLossService;

    //Trigger gain/loss calculation and return success message
    @PostMapping("/calculate/{portfolioId}")
    public ResponseEntity<String> calculateGainLoss(@PathVariable Long portfolioId) {
        gainLossService.calculateAndSaveGainLoss(portfolioId);
        return ResponseEntity.ok("Gain/Loss calculated and saved successfully.");
    }

    // Get gain/loss records for today
    @GetMapping("/today/{portfolioId}")
    public ResponseEntity<List<GainLossDTO>> getTodayGainLoss(@PathVariable Long portfolioId) {
        List<GainLossDTO> gainLossList = gainLossService.getGainLossForPortfolio(portfolioId, LocalDate.now());
        return ResponseEntity.ok(gainLossList);
    }
       // get all today's gain/loss
    @GetMapping("/today")
    public ResponseEntity<List<GainLossDTO>> getAllTodayGainLoss() {
        // Optional helper method in service if needed
        // return ResponseEntity.ok(gainLossService.getAllTodayGainLoss());
        return ResponseEntity.badRequest().body(null); // If not implemented
    }

    // 4. Get total gain amount for a portfolio
    @GetMapping("/total/{portfolioId}")
    public ResponseEntity<Double> getTotalPortfolioGain(@PathVariable Long portfolioId) {
        Double totalGain = gainLossService.getTotalPortfolioGain(portfolioId, LocalDate.now());
        return ResponseEntity.ok(totalGain);
    }
}
