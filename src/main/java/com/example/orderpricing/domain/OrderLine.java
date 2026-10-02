package com.example.orderpricing.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderLine {

    private String productId;
    private String productName;
    private long unitPrice;
    private int saleRatePercent;
    private int quantity;
    private long lineTotal;

    protected OrderLine() {
    }

    public OrderLine(String productId, String productName, long unitPrice,
                     int saleRatePercent, int quantity, long lineTotal) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.saleRatePercent = saleRatePercent;
        this.quantity = quantity;
        this.lineTotal = lineTotal;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public int getSaleRatePercent() {
        return saleRatePercent;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getLineTotal() {
        return lineTotal;
    }
}
