package com.stockapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "portfolios")
public class Portfolio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Portfolio name is required")
    @Size(min = 3, max = 50, message = "Portfolio name must be between 3 and 50 characters")
    @Column(nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Holding> holdings = new ArrayList<>();
    
    // Default Constructor
    public Portfolio() {
    }
    
    // Constructor with parameters
    public Portfolio(String name) {
        this.name = name;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public List<Holding> getHoldings() {
        return holdings;
    }
    
    public void setHoldings(List<Holding> holdings) {
        this.holdings = holdings;
    }
}