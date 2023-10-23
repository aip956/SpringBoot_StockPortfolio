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
    public String showStock(Model model) {
        System.out.println("Show stock");
        List<Stock> stocks = stockService.getAllStock();
        model.addAttribute("stocks", stocks);


        // Total amount invested
        float totalAmountInv = stockService.getTotalAmtInv();
        model.addAttribute("totalAmountInv", totalAmountInv);

        // Remaining to invest
        float totalInvLimit = stockService.getTotalInvLimit(); // $10M
        float remainingAmt = totalInvLimit - totalAmountInv;
        model.addAttribute("totalInvLimit", totalInvLimit);
        model.addAttribute("remainingAmt", remainingAmt);

        return "showStocks";
    }

    // Sorted stock list
    // @GetMapping("/{field}/{order}") in api
    @GetMapping("stock/{field}/{order}")
    public String getStocksWithSort(@PathVariable String field,
                                    @PathVariable String order, Model model) {
        System.out.println("66field: " + field);
        System.out.println("67order: " + order);

        List<Stock> stocks = stockService.findStocksWithSorting(field, order);
        model.addAttribute("stocks", stocks);

         // Total amount invested
         float totalAmountInv = stockService.getTotalAmtInv();
         model.addAttribute("totalAmountInv", totalAmountInv);

         // Remaining to invest
         float totalInvLimit = stockService.getTotalInvLimit(); // $10M
         float remainingAmt = totalInvLimit - totalAmountInv;
         model.addAttribute("totalInvLimit", totalInvLimit);
         model.addAttribute("remainingAmt", remainingAmt);

        return "showStocks";
    }



//    @GetMapping("/pagination/{offset}/{pageSize}")
//    public String getStocksWithPagination(@PathVariable int offset, @PathVariable int pageSize, Model model) {
//        Page<Stock> stocksPage = stockService.findStocksWithPagination(offset, pageSize);
//
//        // Total amount invested
//        float totalAmountInv = stockService.getTotalAmtInv();
//        model.addAttribute("totalAmountInv", totalAmountInv);
//
//        // Remaining to invest
//        float totalInvLimit = 10000000; // $10M
//        float remainingAmt = totalInvLimit - totalAmountInv;
//        model.addAttribute("remainingAmt", remainingAmt);
//        return "showStocks";
//    }
//
//    @GetMapping("/pagination/{offset}/{pageSize}/{field}")
//    // Postman: GET http://localhost:8080/api/stock/pagination/0/3 (first 3 elements)
//    public String getStocksWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field, Model model) {
//        // Total amount invested
//        float totalAmountInv = stockService.getTotalAmtInv();
//        model.addAttribute("totalAmountInv", totalAmountInv);
//
//        // Remaining to invest
//        float totalInvLimit = 10000000; // $10M
//        float remainingAmt = totalInvLimit - totalAmountInv;
//        model.addAttribute("remainingAmt", remainingAmt);
//        return "showStocks";
//    }

    @GetMapping("/addStock")
    public String showAddStock(Model model) {
        model.addAttribute("stock", new Stock());

        // return addStock.html form
        return "addStock";
    }

    @PostMapping("/addStock")
    public String addStock(@ModelAttribute Stock stock, Model model) {

        float totalAmountInv = stockService.getTotalAmtInv();
        float totalInvLimit = stockService.getTotalInvLimit(); // $10M

        System.out.println("amountInv: " + stock.getAmountInv());
        if (stock.getAmountInv() + stockService.getTotalAmtInv() <= totalInvLimit) {
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
        float totalInvLimit = stockService.getTotalInvLimit(); // Max total amount inv
        float newTotalAmtInv = currentTotalAmtInv - stockService.findStockById(stock.getId()).getAmountInv() + stock.getAmountInv();
        float remainingAmt = totalInvLimit - currentTotalAmtInv;
        System.out.println("81newTotalAmtInv: " + newTotalAmtInv);
        System.out.println("87current stock inv: " + stockService.findStockById(stock.getId()).getAmountInv());
        System.out.println("88stock.getAmtInv: " + stock.getAmountInv());
        System.out.println("86totalAmtInv: " + stockService.getTotalAmtInv());
        System.out.println("89remainingAmt: " + stockService.getTotalAmtInv());
        if (newTotalAmtInv <= totalInvLimit) {
            Stock savedStock = stockService.saveStock(stock);
            return "redirect:/stock";
        } else {
            // Add error message attribute to the model
            model.addAttribute("errorMessage", "Total Amount Invested would exceed $10M. Please enter an amount <= $" + (stockService.findStockById(stock.getId()).getAmountInv() + remainingAmt ));
            return "editStock";
        }
        // saves the stock after update and redirects to stock page
    }


    @GetMapping("/deleteStock/{id}")
    public String deleteStock(@PathVariable Long id, Model model) {
        stockService.deleteStockById(id);
        return "redirect:/stock";
    }

    @GetMapping("/editTotalInvLimit")
    public String editTotalInvLimit(Model model) {
        // Retrieve the current totalInvLimit
        float currentTotalInvLimit = stockService.getTotalInvLimit();
        model.addAttribute("currentTotalInvLimit", currentTotalInvLimit);
        return "editTotalInvLimit.html";
    }

    @PostMapping("/editTotalInvLimit")
    public String updateTotalInvLimit(@RequestParam("newLimit") float newLimit, Model model) {
        // Update the totalInvLimit
        stockService.updateTotalInvLimit(newLimit);
        System.out.println("192 newLimit: " + newLimit);
        return "redirect:/stock";
    }
}
