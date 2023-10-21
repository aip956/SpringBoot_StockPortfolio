package com.example.stockportfolio.controller;
import com.example.stockportfolio.model.Stock;
import com.example.stockportfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api/stock")
public class apiController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStock() {
        // Retrieve stock data from the service
        List<Stock> stocks = stockService.getAllStock();
        // Return the data as JSON
        return ResponseEntity.ok(stocks);
    }

   @GetMapping("/{field}")
    public ResponseEntity<List<Stock>> getStocksWithSort(@PathVariable String field) {
        List<Stock> stocks = stockService.findStockWithSorting(field);
        return ResponseEntity.ok(stocks);
    }

}
