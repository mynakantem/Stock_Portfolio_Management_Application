package com.stockapp.repository;

import com.stockapp.model.StockPrice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, String>{
}