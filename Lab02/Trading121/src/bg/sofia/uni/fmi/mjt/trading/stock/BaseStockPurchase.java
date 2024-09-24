package bg.sofia.uni.fmi.mjt.trading.stock;

import bg.sofia.uni.fmi.mjt.trading.DoubleRounding;
import java.time.LocalDateTime;

public abstract class BaseStockPurchase implements StockPurchase {
    private final String stockTicker;
    private final int quantity;
    private final double purchasePricePerUnit;
    private final LocalDateTime purchaseTimestamp;

    protected BaseStockPurchase(String stockTicker, int quantity, LocalDateTime purchaseTimestamp, double purchasePricePerUnit) {
        this.stockTicker = stockTicker;
        this.quantity = quantity;
        this.purchaseTimestamp = purchaseTimestamp;
        this.purchasePricePerUnit = purchasePricePerUnit;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public LocalDateTime getPurchaseTimestamp() {
        return purchaseTimestamp;
    }

    @Override
    public double getPurchasePricePerUnit() {
        return DoubleRounding.round(purchasePricePerUnit);
    }

    @Override
    public double getTotalPurchasePrice() {
        return DoubleRounding.round(purchasePricePerUnit * (double)quantity);
    }

    @Override
    public String getStockTicker() {
        return stockTicker;
    }
}
