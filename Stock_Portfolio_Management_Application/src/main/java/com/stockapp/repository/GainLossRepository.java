package com.stockapp.repository;

import com.stockapp.model.GainLoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface GainLossRepository extends JpaRepository<GainLoss, Long> {
    
    // Find all gain/loss records for a portfolio on a specific date
    List<GainLoss> findByPortfolioIdAndDate(Long portfolioId, LocalDate date);
    
    // Find gain/loss for a specific symbol in a portfolio on a specific date
    GainLoss findByPortfolioIdAndSymbolAndDate(Long portfolioId, String symbol, LocalDate date);
    
    // Get total portfolio gain for a specific date
    @Query("SELECT SUM(gl.gainAmount) FROM GainLoss gl WHERE gl.portfolioId = :portfolioId AND gl.date = :date")
    Double getTotalPortfolioGain(@Param("portfolioId") Long portfolioId, @Param("date") LocalDate date);
    
    // Delete all records for a portfolio (used when portfolio is deleted)
    void deleteByPortfolioId(Long portfolioId);
}