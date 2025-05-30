package com.stockapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gain_loss")
public class GainLoss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "portfolio_id", nullable = false)
    private Long portfolioId;
    @Column(nullable = false)
    private String symbol;
    @Column(name = "gain_amount", nullable = false)
    private Double gainAmount;
    @Column(name = "gain_percentage", nullable = false)
    private Double gainPercentage;
    @Column(name = "daily_change", nullable = false)
    private Double dailyChange;
    @Column(nullable = false)
    private LocalDate date;
}
