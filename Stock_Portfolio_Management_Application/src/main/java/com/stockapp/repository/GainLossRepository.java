package com.stockapp.repository;

import com.stockapp.model.GainLoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface GainLossRepository extends JpaRepository<GainLoss, Long> {
    
    List<GainLoss> findByPortfolioIdAndDate(Long portfolioId, LocalDate date);
    
    @Query("SELECT SUM(g.gainAmount) FROM GainLoss g WHERE g.portfolioId = ?1 AND g.date = ?2")
    Double getTotalPortfolioGain(Long portfolioId, LocalDate date);
    

}