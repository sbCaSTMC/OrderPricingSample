package com.example.orderpricing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {

    @Id
    private String id;

    private String name;

    private long price;

    private int saleRatePercent;

    protected Product() {
    }

    public Product(String id, String name, long price, int saleRatePercent) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.saleRatePercent = saleRatePercent;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public int getSaleRatePercent() {
        return saleRatePercent;
    }
}
