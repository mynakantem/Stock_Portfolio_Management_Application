package com.stockapp.controller;

public class GainLossController {
<<<<<<< Updated upstream

=======
    
    private static final Logger log = LoggerFactory.getLogger(GainLossController.class);
    
    @Autowired
    private GainLossService gainLossService;
    
    // Per-stock gain/loss
    @GetMapping("/stocks/{portfolioId}")
    public List<GainLoss> getStockGainLoss(@PathVariable Long portfolioId) {
        log.info("Getting stock gain/loss for portfolio: {}", portfolioId);
        return gainLossService.calculateStockGainLoss(portfolioId);
    }
    
    // Total portfolio gain/loss
    @GetMapping("/total/{portfolioId}")
    public Double getTotalPortfolioGainLoss(@PathVariable Long portfolioId) {
        log.info("Getting total portfolio gain/loss for: {}", portfolioId);
        return gainLossService.calculateTotalPortfolioGainLoss(portfolioId);
    }
    
    // Daily portfolio gain/loss tracking
    @GetMapping("/daily/{portfolioId}")
    public Double getDailyPortfolioGainLoss(@PathVariable Long portfolioId, 
                                           @RequestParam String date) {
        log.info("Getting daily gain/loss for portfolio: {} on date: {}", portfolioId, date);
        return gainLossService.getDailyPortfolioGainLoss(portfolioId, LocalDate.parse(date));
    }
>>>>>>> Stashed changes
}
