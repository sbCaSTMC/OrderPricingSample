package com.example.orderpricing.service;

import com.example.orderpricing.domain.CustomerRank;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PricingCalculator {

    private static final int MEMBERSHIP_DISCOUNT_PERCENT = 10;
    private static final int MEMBERSHIP_MIN_QUANTITY = 2;

    public record Item(long unitPrice, int saleRatePercent, int quantity) {

        long originalAmount() {
            return unitPrice * quantity;
        }

        long discountedAmount() {
            return originalAmount() * (100 - saleRatePercent) / 100;
        }
    }

    public PricingResult calculate(CustomerRank rank, List<Item> items) {
        long originalSubtotal = items.stream().mapToLong(Item::originalAmount).sum();
        long subtotalAfterSale = items.stream().mapToLong(Item::discountedAmount).sum();
        int totalQuantity = items.stream().mapToInt(Item::quantity).sum();

        long membershipDiscount = 0;
        if (rank == CustomerRank.GOLD && totalQuantity >= MEMBERSHIP_MIN_QUANTITY) {
            membershipDiscount = originalSubtotal * MEMBERSHIP_DISCOUNT_PERCENT / 100;
        }

        return new PricingResult(
                originalSubtotal,
                originalSubtotal - subtotalAfterSale,
                subtotalAfterSale,
                membershipDiscount,
                subtotalAfterSale - membershipDiscount);
    }
}
