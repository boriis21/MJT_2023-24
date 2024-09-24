package bg.sofia.uni.fmi.mjt.trading.price;

import bg.sofia.uni.fmi.mjt.trading.DoubleRounding;
import bg.sofia.uni.fmi.mjt.trading.stock.AmazonStockPurchase;
import bg.sofia.uni.fmi.mjt.trading.stock.GoogleStockPurchase;
import bg.sofia.uni.fmi.mjt.trading.stock.MicrosoftStockPurchase;

public class PriceChart implements PriceChartAPI {
    private double microsoftStockPrice;
    private double googleStockPrice;
    private double amazonStockPrice;

    public PriceChart(double microsoftStockPrice, double googleStockPrice, double amazonStockPrice) {
        this.microsoftStockPrice = microsoftStockPrice;
        this.googleStockPrice = googleStockPrice;
        this.amazonStockPrice = amazonStockPrice;
    }

    @Override
    public double getCurrentPrice(String stockTicker) {
        return switch (stockTicker) {
            case MicrosoftStockPurchase.STOCK_TICKER -> DoubleRounding.round(microsoftStockPrice);
            case GoogleStockPurchase.STOCK_TICKER -> DoubleRounding.round(googleStockPrice);
            case AmazonStockPurchase.STOCK_TICKER -> DoubleRounding.round(amazonStockPrice);
            default -> 0.0;
        };
    }

    @Override
    public boolean changeStockPrice(String stockTicker, int percentChange) {
        if (percentChange < 0) {
            return false;
        }

        switch (stockTicker) {
            case MicrosoftStockPurchase.STOCK_TICKER -> {
                microsoftStockPrice *= (double) percentChange / 100;
                return true;
            }

            case GoogleStockPurchase.STOCK_TICKER -> {
                googleStockPrice *= (double)percentChange/100;
                return true;
            }

            case AmazonStockPurchase.STOCK_TICKER -> {
                amazonStockPrice *= (double)percentChange/100;
                return true;
            }

            default -> {
                return false;
            }
        }
    }
}
