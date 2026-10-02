package com.example.orderpricing.web;

import com.example.orderpricing.domain.CustomerOrder;

import java.util.List;

public record OrderResponse(
        Long orderId,
        String customerId,
        String customerRank,
        List<Line> items,
        long originalSubtotal,
        long saleDiscountTotal,
        long subtotalAfterSale,
        long membershipDiscount,
        long totalAmount) {

    public record Line(
            String productId,
            String productName,
            long unitPrice,
            int saleRatePercent,
            int quantity,
            long lineTotal) {
    }

    public static OrderResponse from(CustomerOrder order) {
        List<Line> lines = order.getLines().stream()
                .map(l -> new Line(l.getProductId(), l.getProductName(), l.getUnitPrice(),
                        l.getSaleRatePercent(), l.getQuantity(), l.getLineTotal()))
                .toList();
        return new OrderResponse(order.getId(), order.getCustomerId(), order.getCustomerRank().name(),
                lines, order.getOriginalSubtotal(), order.getSaleDiscountTotal(),
                order.getSubtotalAfterSale(), order.getMembershipDiscount(), order.getTotalAmount());
    }
}
