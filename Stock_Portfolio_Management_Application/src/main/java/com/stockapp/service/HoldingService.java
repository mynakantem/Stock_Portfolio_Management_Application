package com.stockapp.service;

import com.stockapp.dto.HoldingDTO;
import com.stockapp.exception.PortfolioNotFoundException;
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

    public HoldingDTO addHolding(HoldingDTO dto) {
        logger.info("Adding new holding for symbol: {} to portfolio: {}", dto.getSymbol(), dto.getPortfolioId());

        Portfolio portfolio = portfolioRepository.findById(dto.getPortfolioId())
                .orElseThrow(() -> new PortfolioNotFoundException(dto.getPortfolioId()));

        Holding holding = new Holding();
        holding.setSymbol(dto.getSymbol().toUpperCase());
        holding.setQuantity(dto.getQuantity());
        holding.setBuyPrice(dto.getBuyPrice());
        holding.setPortfolio(portfolio);

        Holding saved = holdingRepository.save(holding);
        logger.info("Holding saved with ID: {}", saved.getId());

        return convertToDTO(saved);
    }

    public List<HoldingDTO> getAllHoldingsForPortfolio(Long portfolioId) {
        logger.info("Fetching all holdings for portfolio ID: {}", portfolioId);

        List<Holding> holdings = holdingRepository.findByPortfolioId(portfolioId);

        return holdings.stream().map(holding -> {
            HoldingDTO dto = convertToDTO(holding);

            try {
                StockPrice price = stockPriceRepository.findById(holding.getSymbol()).orElse(null);
                if (price != null) {
                    dto.setCurrentPrice(price.getPrice());
                }

                List<GainLoss> gl = gainLossRepository.findByPortfolioIdAndDate(portfolioId, LocalDate.now());
                gl.stream()
                        .filter(g -> g.getSymbol().equals(holding.getSymbol()))
                        .findFirst()
                        .ifPresent(gain -> {
                            dto.setGainAmount(gain.getGainAmount());
                            dto.setGainPercentage(gain.getGainPercentage());
                        });

            } catch (Exception e) {
                logger.error("Error while enriching holding with market data: {}", e.getMessage());
            }

            return dto;
        }).collect(Collectors.toList());
    }

    public HoldingDTO getHoldingById(Long id) {
        logger.info("Fetching holding by ID: {}", id);

        Holding holding = holdingRepository.findById(id).orElse(null);
        if (holding == null) {
            logger.warn("Holding not found with ID: {}", id);
            return null;
        }

        HoldingDTO dto = convertToDTO(holding);

        try {
            StockPrice price = stockPriceRepository.findById(holding.getSymbol()).orElse(null);
            if (price != null) {
                dto.setCurrentPrice(price.getPrice());
            }

            List<GainLoss> gl = gainLossRepository.findByPortfolioIdAndDate(holding.getPortfolio().getId(), LocalDate.now());
            gl.stream()
                    .filter(g -> g.getSymbol().equals(holding.getSymbol()))
                    .findFirst()
                    .ifPresent(gain -> {
                        dto.setGainAmount(gain.getGainAmount());
                        dto.setGainPercentage(gain.getGainPercentage());
                    });

        } catch (Exception e) {
            logger.error("Error while enriching holding {} with market data: {}", id, e.getMessage());
        }

        return dto;
    }

    public HoldingDTO updateHolding(Long id, HoldingDTO dto) {
        logger.info("Updating holding ID: {}", id);

        Holding holding = holdingRepository.findById(id).orElse(null);
        if (holding == null) {
            logger.warn("Holding not found with ID: {}", id);
            return null;
        }

        holding.setQuantity(dto.getQuantity());
        holding.setBuyPrice(dto.getBuyPrice());

        Holding updated = holdingRepository.save(holding);
        logger.info("Holding updated successfully");

        return convertToDTO(updated);
    }

    public boolean deleteHolding(Long id) {
        logger.info("Deleting holding ID: {}", id);

        if (!holdingRepository.existsById(id)) {
            logger.warn("Holding not found with ID: {}", id);
            return false;
        }

        holdingRepository.deleteById(id);
        logger.info("Holding deleted successfully");
        return true;
    }

    private HoldingDTO convertToDTO(Holding holding) {
        logger.debug("Converting holding to DTO for ID: {}", holding.getId());

        HoldingDTO dto = new HoldingDTO();
        dto.setId(holding.getId());
        dto.setSymbol(holding.getSymbol());
        dto.setQuantity(holding.getQuantity());
        dto.setBuyPrice(holding.getBuyPrice());

        if (holding.getPortfolio() != null) {
            dto.setPortfolioId(holding.getPortfolio().getId());
        } else {
            logger.error("❌ Portfolio is null for holding ID: {}", holding.getId());
            dto.setPortfolioId(null);
        }

        return dto;
    }
}
