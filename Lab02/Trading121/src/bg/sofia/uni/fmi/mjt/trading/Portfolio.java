package bg.sofia.uni.fmi.mjt.trading;

import bg.sofia.uni.fmi.mjt.trading.price.PriceChartAPI;
import bg.sofia.uni.fmi.mjt.trading.stock.AmazonStockPurchase;
import bg.sofia.uni.fmi.mjt.trading.stock.GoogleStockPurchase;
import bg.sofia.uni.fmi.mjt.trading.stock.MicrosoftStockPurchase;
import bg.sofia.uni.fmi.mjt.trading.stock.StockPurchase;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Portfolio implements PortfolioAPI {
    private static final int STOCK_PRICE_INCREASE_PERCENT = 5;
    private final String owner;
    private final PriceChartAPI priceChart;
    private StockPurchase[] stockPurchases;
    private double budget;
    private int size;
    private final int maxSize;

    public Portfolio(String owner, PriceChartAPI priceChart, double budget, int maxSize) {
        this(owner, priceChart, new StockPurchase[] {}, budget, maxSize);
    }

    public Portfolio(String owner, PriceChartAPI priceChart, StockPurchase[] stockPurchases, double budget, int maxSize) {
        this.owner = owner;
        this.priceChart = priceChart;
        this.stockPurchases = Arrays.copyOf(stockPurchases, maxSize);
        this.budget = budget;
        this.maxSize = maxSize;
    }

    public StockPurchase buyStock(String stockTicker, int quantity) {
        if (quantity < 0 || size == maxSize) {
            return null;
        }

        switch (stockTicker) {
            case AmazonStockPurchase.STOCK_TICKER -> {
                return tryToPurchase(new AmazonStockPurchase(quantity, LocalDateTime.now(), priceChart.getCurrentPrice(stockTicker)));
            }

            case GoogleStockPurchase.STOCK_TICKER -> {
                return tryToPurchase(new GoogleStockPurchase(quantity, LocalDateTime.now(), priceChart.getCurrentPrice(stockTicker)));
            }

            case MicrosoftStockPurchase.STOCK_TICKER -> {
                return tryToPurchase(new MicrosoftStockPurchase(quantity, LocalDateTime.now(), priceChart.getCurrentPrice(stockTicker)));
            }

            default -> {
                return null;
            }
        }
    }

    private StockPurchase tryToPurchase(StockPurchase purchase) {
        double amountToPay = purchase.getTotalPurchasePrice();
        if (amountToPay > budget) {
            return null;
        }

        stockPurchases[size++] = purchase;
        budget -= amountToPay;

        priceChart.changeStockPrice(purchase.getStockTicker(), STOCK_PRICE_INCREASE_PERCENT);

        return purchase;
    }

    public StockPurchase[] getAllPurchases() {
        return Arrays.copyOf(stockPurchases, size);
    }

    public StockPurchase[] getAllPurchases(LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
        int purchaseSize = countInTimeRange(startTimestamp, endTimestamp);
        StockPurchase[] purchases = new StockPurchase[purchaseSize];
        int sizeIter = 0;

        for (StockPurchase purchase : stockPurchases) {
            if (timestampInRange(purchase.getPurchaseTimestamp(), startTimestamp, endTimestamp)) {
                purchases[sizeIter++] = purchase;
            }
        }

        return purchases;
    }

    private boolean timestampInRange(LocalDateTime purchaseTimestamp, LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
        return ((purchaseTimestamp.isEqual(startTimestamp) || purchaseTimestamp.isAfter(startTimestamp)) &&
                (purchaseTimestamp.isEqual(endTimestamp) || purchaseTimestamp.isBefore(endTimestamp)));
    }

    private int countInTimeRange(LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
        int count = 0;
        for (StockPurchase purchase : stockPurchases) {
            if (timestampInRange(purchase.getPurchaseTimestamp(), startTimestamp, endTimestamp)) {
                count++;
            }
        }
        return count;
    }

    public double getNetWorth() {
        double netWorth = 0;

        for (StockPurchase purchase : stockPurchases) {
            String stockTicker = purchase.getStockTicker();
            netWorth += (purchase.getQuantity() * priceChart.getCurrentPrice(stockTicker));
        }

        return DoubleRounding.round(netWorth);
    }

    public double getRemainingBudget() {
        return DoubleRounding.round(budget);
    }

    public String getOwner() {
        return owner;
    }
}
