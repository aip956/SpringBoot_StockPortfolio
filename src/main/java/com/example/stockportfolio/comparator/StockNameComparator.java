package com.example.stockportfolio.comparator;
import com.example.stockportfolio.model.Stock;
import java.util.Comparator;

public class StockNameComparator implements Comparator<Stock> {
    @Override
    public int compare(Stock stock1, Stock stock2) {
        return stock1.getStockName().compareTo(stock2.getStockName());
    }
}

