package com.stockapp.repository;

import com.stockapp.model.Alert;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
	List<Alert> findBySymbol(String symbol);
}
