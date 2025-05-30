package com.stockapp.repository;

import com.stockapp.model.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Database access for holdings.
 */
@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {

    // Fetch all holdings by portfolio ID
    List<Holding> findByPortfolioId(Long portfolioId);
}
