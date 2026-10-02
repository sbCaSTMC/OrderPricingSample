package com.example.orderpricing.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerId;

    @Enumerated(EnumType.STRING)
    private CustomerRank customerRank;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_lines", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderLine> lines = new ArrayList<>();

    private long originalSubtotal;
    private long saleDiscountTotal;
    private long subtotalAfterSale;
    private long membershipDiscount;
    private long totalAmount;

    protected CustomerOrder() {
    }

    public CustomerOrder(String customerId, CustomerRank customerRank, List<OrderLine> lines,
                         long originalSubtotal, long saleDiscountTotal, long subtotalAfterSale,
                         long membershipDiscount, long totalAmount) {
        this.customerId = customerId;
        this.customerRank = customerRank;
        this.lines = new ArrayList<>(lines);
        this.originalSubtotal = originalSubtotal;
        this.saleDiscountTotal = saleDiscountTotal;
        this.subtotalAfterSale = subtotalAfterSale;
        this.membershipDiscount = membershipDiscount;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public CustomerRank getCustomerRank() {
        return customerRank;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public long getOriginalSubtotal() {
        return originalSubtotal;
    }

    public long getSaleDiscountTotal() {
        return saleDiscountTotal;
    }

    public long getSubtotalAfterSale() {
        return subtotalAfterSale;
    }

    public long getMembershipDiscount() {
        return membershipDiscount;
    }

    public long getTotalAmount() {
        return totalAmount;
    }
}
