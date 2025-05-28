package com.stockapp.repository;

import com.stockapp.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    
    // Find all portfolios for a specific user
    List<Portfolio> findByUserId(Long userId);
    
    // Find portfolio by ID (this is already provided by JpaRepository)
    // Optional<Portfolio> findById(Long id);
    
    // Additional methods that might be useful for GainLoss module:
    
    // Check if a portfolio exists
    boolean existsById(Long portfolioId);
    
    // Find portfolio by name
    Optional<Portfolio> findByName(String name);
    
    // Count portfolios for a user
    long countByUserId(Long userId);
}