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
        // List of stocks
         System.out.println("Show stock");
        List<Stock> stocks = stockService.getAllStock();
        model.addAttribute("stocks", stocks);

        // Total amount invested
        float totalAmountInv = stockService.getTotalAmtInv();
        model.addAttribute("totalAmountInv", totalAmountInv);

        // Remaining to invest
        float totalInvLimit = 10000000; // $10M
        float remainingAmt = totalInvLimit - totalAmountInv;
        model.addAttribute("remainingAmt", remainingAmt);

        return "showStocks";
    }

    @GetMapping("/addStock")
    public String showAddStock(Model model) {
        model.addAttribute("stock", new Stock());

        // return addStock.html form
        return "addStock";
    }

    @PostMapping("/addStock")
    public String addStock(@ModelAttribute Stock stock, Model model) {

        float totalAmountInv = stockService.getTotalAmtInv();
        System.out.println("amountInv: " + stock.getAmountInv());
        if (stock.getAmountInv() + stockService.getTotalAmtInv() <= 10000000.0f) {
            Stock savedStock = stockService.saveStock(stock);
        } else {
            // Add error message attribute to the model
            model.addAttribute("errorMessage", "Total Amount Invested would exceed $10M. Please enter an amount <= $" + (10000000.0f - stockService.getTotalAmtInv()));
            return "addStock";
        }
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

    // issue with edit; if remaining amount = 0; and current stock is 850K; cannot edit to < 850K
    @PostMapping("/updateStock")
    public String updateStock(@ModelAttribute Stock stock, Model model) {
        float currentTotalAmtInv = stockService.getTotalAmtInv(); // Total amount currently invested
        float maxTotalAmtInv = 10000000.0f; // Max total amount inv
        float newTotalAmtInv = currentTotalAmtInv - stockService.findStockById(stock.getId()).getAmountInv() + stock.getAmountInv();
        System.out.println("81newTotalAmtInv: " + newTotalAmtInv);
        System.out.println("87current stock inv: " + stockService.findStockById(stock.getId()).getAmountInv());
        System.out.println("88stock.getAmtInv: " + stock.getAmountInv());
        System.out.println("86totalAmtInv: " + stockService.getTotalAmtInv());
        if (newTotalAmtInv <= 10000000.0f) {
            Stock savedStock = stockService.saveStock(stock);
            return "redirect:/stock";
        } else {
            // Add error message attribute to the model
            model.addAttribute("errorMessage", "Total Amount Invested would exceed $10M. Please enter an amount <= $" + (10000000.0f - stockService.getTotalAmtInv()));
            return "editStock";
        }
        // saves the stock after update and redirects to stock page
    }

    @GetMapping("/deleteStock/{id}")
    public String deleteStock(@PathVariable Long id, Model model) {
        stockService.deleteStockById(id);
        return "redirect:/stock";
    }
}
