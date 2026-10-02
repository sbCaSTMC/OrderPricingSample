package com.example.orderpricing.service;

import com.example.orderpricing.domain.Customer;
import com.example.orderpricing.domain.CustomerOrder;
import com.example.orderpricing.domain.OrderLine;
import com.example.orderpricing.domain.Product;
import com.example.orderpricing.repository.CustomerRepository;
import com.example.orderpricing.repository.OrderRepository;
import com.example.orderpricing.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    public record RequestedItem(String productId, int quantity) {
    }

    private final CustomerRepository customers;
    private final ProductRepository products;
    private final OrderRepository orders;
    private final PricingCalculator calculator;

    public OrderService(CustomerRepository customers, ProductRepository products,
                        OrderRepository orders, PricingCalculator calculator) {
        this.customers = customers;
        this.products = products;
        this.orders = orders;
        this.calculator = calculator;
    }

    @Transactional
    public CustomerOrder createOrder(String customerId, List<RequestedItem> requestedItems) {
        Customer customer = customers.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found: " + customerId));

        List<Product> resolved = new ArrayList<>();
        List<PricingCalculator.Item> pricingItems = new ArrayList<>();
        for (RequestedItem requested : requestedItems) {
            Product product = products.findById(requested.productId())
                    .orElseThrow(() -> new NotFoundException("Product not found: " + requested.productId()));
            resolved.add(product);
            pricingItems.add(new PricingCalculator.Item(
                    product.getPrice(), product.getSaleRatePercent(), requested.quantity()));
        }

        PricingResult result = calculator.calculate(customer.getRank(), pricingItems);

        List<OrderLine> lines = new ArrayList<>();
        for (int i = 0; i < resolved.size(); i++) {
            Product product = resolved.get(i);
            PricingCalculator.Item item = pricingItems.get(i);
            lines.add(new OrderLine(product.getId(), product.getName(), product.getPrice(),
                    product.getSaleRatePercent(), item.quantity(), item.discountedAmount()));
        }

        return orders.save(new CustomerOrder(
                customer.getId(), customer.getRank(), lines,
                result.originalSubtotal(), result.saleDiscountTotal(), result.subtotalAfterSale(),
                result.membershipDiscount(), result.totalAmount()));
    }
}
