package com.example.stockportfolio.service;

import com.example.stockportfolio.model.Stock;
import com.example.stockportfolio.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StockService {
    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStock() {return stockRepository.findAll();}
    public Stock findStockById(Long id) { return stockRepository.findById(id).orElse(null); }
    public Stock saveStock(Stock stock) { return stockRepository.save(stock); }
    public void deleteStockById(Long id) { stockRepository.deleteById(id); }
    public float getTotalAmtInv() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream()
                .map(Stock::getAmountInv)
                .reduce(0.0f, Float::sum);
    }
}
