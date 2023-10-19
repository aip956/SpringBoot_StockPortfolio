package com.example.stockportfolio.repository;
import com.example.stockportfolio.model.Stock;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
