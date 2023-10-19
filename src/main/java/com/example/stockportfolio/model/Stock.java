package com.example.stockportfolio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stockName;
    private float amountInv;

    public Stock() {
        // Default no-arg constructor required by JPA
    }

    @Override
    // toString
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", stockName='" + stockName + '\'' +
                ", amountInv=" + amountInv +
                '}';
    }

    // Constructor
    public Stock(Long id, String stockName, float amountInv) {
        this.id = id;
        this.stockName = stockName;
        this.amountInv = amountInv;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public float getAmountInv() {
        return amountInv;
    }

    public void setAmountInv(float amountInv) {
        this.amountInv = amountInv;
    }
}
