package com.stockapp.service;

import com.stockapp.dto.GainLossDTO;
import com.stockapp.model.GainLoss;
import com.stockapp.model.Holding;
import com.stockapp.model.StockPrice;
import com.stockapp.repository.GainLossRepository;
import com.stockapp.repository.HoldingRepository;
import com.stockapp.repository.StockPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GainLossService {

    private static final Logger logger = LoggerFactory.getLogger(GainLossService.class);

    @Autowired
    private GainLossRepository gainLossRepository;

    @Autowired
    private HoldingRepository holdingRepository;

    @Autowired
    private StockPriceRepository stockPriceRepository;

    // Calculate and save gain/loss for all holdings in a portfolio
    public void calculateAndSaveGainLoss(Long portfolioId) {
        logger.info("Calculating gain/loss for portfolio: {}", portfolioId);

        List<Holding> holdings = holdingRepository.findByPortfolioId(portfolioId);
        LocalDate today = LocalDate.now();

        for (Holding holding : holdings) {
            StockPrice stockPrice = stockPriceRepository.findByStockSymbol(holding.getSymbol()).orElse(null);

            if (stockPrice != null) {
                double currentPrice = stockPrice.getPrice();
                double buyPrice = holding.getBuyPrice();
                int quantity = holding.getQuantity();

                double gainAmount = (currentPrice - buyPrice) * quantity;
                double gainPercentage = (gainAmount / (buyPrice * quantity)) * 100;

                GainLoss previousGainLoss = gainLossRepository.findByPortfolioIdAndSymbolAndDate(
                        portfolioId, holding.getSymbol(), today.minusDays(1));

                double dailyChange = previousGainLoss != null
                        ? Math.abs(gainAmount - previousGainLoss.getGainAmount())
                        : Math.abs(gainAmount);

                GainLoss existingRecord = gainLossRepository.findByPortfolioIdAndSymbolAndDate(
                        portfolioId, holding.getSymbol(), today);

                if (existingRecord != null) {
                    // Update
                    existingRecord.setGainAmount(gainAmount);
                    existingRecord.setGainPercentage(gainPercentage);
                    existingRecord.setDailyChange(dailyChange);
                    gainLossRepository.save(existingRecord);
                    logger.info("Updated gain/loss for symbol: {} in portfolio: {}", holding.getSymbol(), portfolioId);
                } else {
                    // Create and save
                    GainLoss newGainLoss = new GainLoss(
                            null,
                            portfolioId,
                            holding.getSymbol(),
                            gainAmount,
                            gainPercentage,
                            dailyChange,
                            today
                    );
                    gainLossRepository.save(newGainLoss); // ✅ FIXED: save new record
                    logger.info("Created new gain/loss for symbol: {} in portfolio: {}", holding.getSymbol(), portfolioId);
                }
            } else {
                logger.warn("No stock price found for symbol: {}", holding.getSymbol());
            }
        }
    }

    // Get gain/loss data for a portfolio on a specific date
    public List<GainLossDTO> getGainLossForPortfolio(Long portfolioId, LocalDate date) {
        logger.info("Fetching gain/loss for portfolio: {} on date: {}", portfolioId, date);

        List<GainLoss> gainLossList = gainLossRepository.findByPortfolioIdAndDate(portfolioId, date);

        return gainLossList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get total portfolio gain/loss for a specific date
    public Double getTotalPortfolioGain(Long portfolioId, LocalDate date) {
        logger.info("Calculating total portfolio gain for portfolio: {} on date: {}", portfolioId, date);

        Double totalGain = gainLossRepository.getTotalPortfolioGain(portfolioId, date);
        return totalGain != null ? totalGain : 0.0;
    }

    // Get gain/loss for a specific holding
    public GainLossDTO getGainLossForHolding(Long portfolioId, String symbol, LocalDate date) {
        logger.info("Fetching gain/loss for symbol: {} in portfolio: {} on date: {}", symbol, portfolioId, date);

        GainLoss gainLoss = gainLossRepository.findByPortfolioIdAndSymbolAndDate(portfolioId, symbol, date);

        return gainLoss != null ? convertToDTO(gainLoss) : null;
    }

    // Delete all gain/loss records for a portfolio
    public void deleteGainLossForPortfolio(Long portfolioId) {
        logger.info("Deleting all gain/loss records for portfolio: {}", portfolioId);
        gainLossRepository.deleteByPortfolioId(portfolioId);
    }

    // Helper method to convert GainLoss to GainLossDTO
    private GainLossDTO convertToDTO(GainLoss gainLoss) {
        GainLossDTO dto = new GainLossDTO();
        dto.setId(gainLoss.getId());
        dto.setPortfolioId(gainLoss.getPortfolioId());
        dto.setSymbol(gainLoss.getSymbol());
        dto.setGainAmount(gainLoss.getGainAmount());
        dto.setGainPercentage(gainLoss.getGainPercentage());
        dto.setDailyChange(gainLoss.getDailyChange());
        dto.setDate(gainLoss.getDate());
        return dto;
    }
}
