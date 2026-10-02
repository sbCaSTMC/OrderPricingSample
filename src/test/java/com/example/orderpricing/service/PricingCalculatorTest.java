package com.example.orderpricing.service;

import com.example.orderpricing.domain.CustomerRank;
import com.example.orderpricing.service.PricingCalculator.Item;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingCalculatorTest {

    private final PricingCalculator calculator = new PricingCalculator();

    @Test
    void goldMembershipDiscountIsAppliedToSubtotalAfterSale() {
        PricingResult result = calculator.calculate(CustomerRank.GOLD, List.of(
                new Item(10000, 0, 1),
                new Item(5000, 20, 1)));

        assertEquals(15000, result.originalSubtotal());
        assertEquals(1000, result.saleDiscountTotal());
        assertEquals(14000, result.subtotalAfterSale());
        assertEquals(1400, result.membershipDiscount());
        assertEquals(12600, result.totalAmount());
    }

    @Test
    void goldMembershipDiscountWithoutSaleProductsIsUnchanged() {
        PricingResult result = calculator.calculate(CustomerRank.GOLD, List.of(
                new Item(10000, 0, 2)));

        assertEquals(20000, result.subtotalAfterSale());
        assertEquals(2000, result.membershipDiscount());
        assertEquals(18000, result.totalAmount());
    }

    @Test
    void goldCustomerBelowMinimumQuantityGetsNoMembershipDiscount() {
        PricingResult result = calculator.calculate(CustomerRank.GOLD, List.of(
                new Item(5000, 20, 1)));

        assertEquals(0, result.membershipDiscount());
        assertEquals(4000, result.totalAmount());
    }

    @Test
    void regularCustomerGetsNoMembershipDiscount() {
        PricingResult result = calculator.calculate(CustomerRank.REGULAR, List.of(
                new Item(10000, 0, 1),
                new Item(5000, 20, 1)));

        assertEquals(0, result.membershipDiscount());
        assertEquals(14000, result.totalAmount());
    }
}
