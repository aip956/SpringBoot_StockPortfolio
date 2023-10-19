package com.example.stockportfolio.controller;
import com.example.stockportfolio.model.Stock;
import com.example.stockportfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class stockController {
    // need to create service!!
     private final StockService stockService;

    @Autowired
    public stockController(StockService stockService) {
        this.stockService = stockService;
    }
//    @GetMapping("/test1")
//    @ResponseBody
//    public String printHello() {
//        // return this text
//        return "Next up!";
//    }

    @GetMapping("/stock")
    public String showStock(Model model) {
        List<Stock> stock = stockService.getAllStock();
        model.addAttribute("stock", stock);
        return "showStocks";
    }

    @GetMapping("/addStock")
    public String showAddStock(Model model) {
        model.addAttribute("stock", new Stock());
        // return addStock.html form
        return "addStock";
    }

}
