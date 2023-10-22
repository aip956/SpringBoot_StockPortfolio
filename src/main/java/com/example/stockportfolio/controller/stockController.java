package com.example.stockportfolio.controller;
import com.example.stockportfolio.comparator.StockAmountComparator;
import com.example.stockportfolio.comparator.StockNameComparator;
import com.example.stockportfolio.model.Stock;
import com.example.stockportfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public String showStock(Model model, @RequestParam(name = "sortOption", required = false) String selectedOption) {
        //  public String showStock(Model model, @RequestParam(name = "sortOption", required = false) String selectedOption) {
        // List of stocks
        if (selectedOption ==  null) {
            selectedOption = "amountAsc";
            System.out.println("selectedOption null");
        }

        System.out.println("Show stock");

        List<Stock> stocks = stockService.getAllStock();
        model.addAttribute("selectedOption", selectedOption);
        System.out.println("45selectedOption: " + selectedOption);
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

    // Sorted stock list
    @GetMapping("/{field}")
    public String getStocksWithSort(@PathVariable String field,
                                    @RequestParam(name = "order", defaultValue = "descending") String sortOrder, Model model) {
        List<Stock> stocks = stockService.findStocksWithSorting(field, sortOrder);

         // Total amount invested
         float totalAmountInv = stockService.getTotalAmtInv();
         model.addAttribute("totalAmountInv", totalAmountInv);

         // Remaining to invest
         float totalInvLimit = 10000000; // $10M
         float remainingAmt = totalInvLimit - totalAmountInv;
         model.addAttribute("remainingAmt", remainingAmt);
        return "showStocks";
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    public String getStocksWithPagination(@PathVariable int offset, @PathVariable int pageSize, Model model) {
        Page<Stock> stocksPage = stockService.findStocksWithPagination(offset, pageSize);

        // Total amount invested
        float totalAmountInv = stockService.getTotalAmtInv();
        model.addAttribute("totalAmountInv", totalAmountInv);

        // Remaining to invest
        float totalInvLimit = 10000000; // $10M
        float remainingAmt = totalInvLimit - totalAmountInv;
        model.addAttribute("remainingAmt", remainingAmt);
        return "showStocks";
    }

    @GetMapping("/pagination/{offset}/{pageSize}/{field}")
    // Postman: GET http://localhost:8080/api/stock/pagination/0/3 (first 3 elements)
    public String getStocksWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field, Model model) {
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
