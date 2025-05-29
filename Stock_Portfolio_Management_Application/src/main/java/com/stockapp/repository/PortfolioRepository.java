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
    
    // Check if a portfolio exists
    boolean existsById(Long portfolioId);
    
    // Find portfolio by name and user
    Optional<Portfolio> findByNameAndUserId(String name, Long userId);
    
    // Count portfolios for a user
    long countByUserId(Long userId);
}