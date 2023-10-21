package com.example.stockportfolio.comparator;

import com.example.stockportfolio.model.Stock;
import java.util.Comparator;

public class StockAmountComparator implements Comparator<Stock> {
    @Override
    public int compare(Stock stock1, Stock stock2) {
        return Float.compare(stock1.getAmountInv(), stock2.getAmountInv());
    }
}
