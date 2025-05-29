package com.stockapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

// Represents a user portfolio that contains multiple stock holdings.

@Data
@NoArgsConstructor
@Entity
@Table(name = "portfolios")
public class Portfolio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Portfolio name like "Tech Stocks", "My Long Term", etc.
    @NotBlank(message = "Portfolio name is required")
    @Size(min = 3, max = 50, message = "Portfolio name must be between 3 and 50 characters")
    @Column(nullable = false)
    private String name;
    
    // Each portfolio belongs to one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    // Portfolio can contain many holdings
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Holding> holdings = new ArrayList<>();  
   
}