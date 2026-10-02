package com.example.orderpricing.service;

public record PricingResult(
        long originalSubtotal,
        long saleDiscountTotal,
        long subtotalAfterSale,
        long membershipDiscount,
        long totalAmount) {
}
