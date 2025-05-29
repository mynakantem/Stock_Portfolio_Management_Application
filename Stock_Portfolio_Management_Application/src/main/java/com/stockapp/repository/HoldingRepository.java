package com.stockapp.repository;

import com.stockapp.model.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {
    // Find all holdings for a specific portfolio
    List<Holding> findByPortfolioId(Long portfolioId);
}