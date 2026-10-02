package com.example.orderpricing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Customer {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private CustomerRank rank;

    protected Customer() {
    }

    public Customer(String id, CustomerRank rank) {
        this.id = id;
        this.rank = rank;
    }

    public String getId() {
        return id;
    }

    public CustomerRank getRank() {
        return rank;
    }
}
