package edu.hw3.task6;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Market implements StockMarket {
    public static final Comparator<Stock> COAST_COMPARATOR = Comparator
        .comparingDouble(Stock::getPrice).reversed();
    private final PriorityQueue<Stock> stocks = new PriorityQueue<>(COAST_COMPARATOR);

    @Override
    public void add(Stock stock) {

        stocks.add(stock);
    }

    @Override
    public void remove(Stock stock) {

        stocks.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {

        return stocks.peek();
    }
}
