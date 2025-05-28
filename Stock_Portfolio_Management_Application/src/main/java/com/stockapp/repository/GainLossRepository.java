package com.stockapp.repository;

import com.stockapp.model.GainLoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GainLossRepository extends JpaRepository<GainLoss, Long> {
    
    // Find all gain/loss records for a specific portfolio
    List<GainLoss> findByPortfolioName(String portfolioName);
    
    // Find gain/loss record for a specific symbol
    GainLoss findBySymbol(String symbol);
}