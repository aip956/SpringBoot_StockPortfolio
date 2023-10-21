package com.example.stockportfolio.controller;
import com.example.stockportfolio.model.Stock;
import com.example.stockportfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    // Postman: http://localhost:8080/api/stock
    public ResponseEntity<List<Stock>> getAllStock() {
        // Retrieve stock data from the service
        List<Stock> stocks = stockService.getAllStock();
        // Return the data as JSON
        return ResponseEntity.ok(stocks);
    }

   @GetMapping("/{field}")
   // Postman: GET http://localhost:8080/api/stock/amountInv
    public ResponseEntity<List<Stock>> getStocksWithSort(@PathVariable String field) {
        List<Stock> stocks = stockService.findStocksWithSorting(field);
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    // Postman: GET http://localhost:8080/api/stock/pagination/0/3 (first 3 elements)
    public Page getStocksWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<Stock> stocksPage = stockService.findStocksWithPagination(offset, pageSize);
        return stockService.findStocksWithPagination(offset, pageSize);
    }

    @GetMapping("/pagination/{offset}/{pageSize}/{field}")
    // Postman: GET http://localhost:8080/api/stock/pagination/0/3 (first 3 elements)
    public Page getStocksWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {
        Page<Stock> stocksPage = stockService.findStocksWithPaginationAndSort(offset, pageSize, field);
        return stocksPage;
    }
}
