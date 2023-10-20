package com.example.stockportfolio.controller;
import com.example.stockportfolio.model.Stock;
import com.example.stockportfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<Stock> stocks = stockService.getAllStock();
        System.out.println("In showStock" + stocks);
        model.addAttribute("stocks", stocks);
        return "showStocks";
    }

    @GetMapping("/addStock")
    public String showAddStock(Model model) {
        model.addAttribute("stock", new Stock());

        // return addStock.html form
        return "addStock";
    }

    @PostMapping("/addStock")
    public String addStock(@ModelAttribute Stock stock) {
        Stock savedStock = stockService.saveStock(stock);
        System.out.println("In addStock: " + stock);
        // saves the stock after adding
        return "redirect:/stock";
    }

    @GetMapping("/editStock/{id}")
    public String editStock(@PathVariable Long id, Model model) {
        Stock stockToEdit = stockService.findStockById(id);
        if(stockToEdit != null) {
            model.addAttribute("stock", stockToEdit);
            return "editStock";

        } else {
            return "stockNotFound";
        }
    }

    @PostMapping("/updateStock")
    public String updateStock(@ModelAttribute Stock stock) {
        stockService.saveStock(stock);
        // saves the stock after update and redirects to stock page
        return "redirect:/stock";
    }

    @GetMapping("/deleteStock/{id}")
    public String deleteStock(@PathVariable Long id, Model model) {
        stockService.deleteStockById(id);
        return "redirect:/stock";
    }
}
