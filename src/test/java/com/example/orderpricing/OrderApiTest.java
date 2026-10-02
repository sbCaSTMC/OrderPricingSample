package com.example.orderpricing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderApiTest {

    @Autowired
    MockMvc mockMvc;

    private ResultActions order(String json) throws Exception {
        return mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    @Test
    void regularCustomerWithRegularProduct() throws Exception {
        order("""
                {"customerId":"C001","items":[{"productId":"P001","quantity":1}]}
                """)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.membershipDiscount").value(0))
                .andExpect(jsonPath("$.totalAmount").value(10000));
    }

    @Test
    void regularCustomerWithSaleProduct() throws Exception {
        order("""
                {"customerId":"C001","items":[{"productId":"P002","quantity":1}]}
                """)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.saleDiscountTotal").value(1000))
                .andExpect(jsonPath("$.membershipDiscount").value(0))
                .andExpect(jsonPath("$.totalAmount").value(4000));
    }

    @Test
    void goldCustomerWithTwoRegularProducts() throws Exception {
        order("""
                {"customerId":"C002","items":[{"productId":"P001","quantity":2}]}
                """)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.originalSubtotal").value(20000))
                .andExpect(jsonPath("$.membershipDiscount").value(2000))
                .andExpect(jsonPath("$.totalAmount").value(18000));
    }

    @Test
    void goldCustomerWithOneSaleProduct() throws Exception {
        order("""
                {"customerId":"C002","items":[{"productId":"P002","quantity":1}]}
                """)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.membershipDiscount").value(0))
                .andExpect(jsonPath("$.totalAmount").value(4000));
    }

    @Test
    void goldCustomerWithRegularAndSaleProducts() throws Exception {
        order("""
                {"customerId":"C002","items":[{"productId":"P001","quantity":1},{"productId":"P002","quantity":1}]}
                """)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.originalSubtotal").value(15000))
                .andExpect(jsonPath("$.saleDiscountTotal").value(1000))
                .andExpect(jsonPath("$.subtotalAfterSale").value(14000))
                .andExpect(jsonPath("$.membershipDiscount").value(1400))
                .andExpect(jsonPath("$.totalAmount").value(12600));
    }

    @Test
    void goldCustomerWithMultipleSaleProducts() throws Exception {
        order("""
                {"customerId":"C002","items":[{"productId":"P002","quantity":2}]}
                """)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.originalSubtotal").value(10000))
                .andExpect(jsonPath("$.saleDiscountTotal").value(2000))
                .andExpect(jsonPath("$.subtotalAfterSale").value(8000))
                .andExpect(jsonPath("$.membershipDiscount").value(800))
                .andExpect(jsonPath("$.totalAmount").value(7200));
    }

    @Test
    void goldCustomerWithMultipleRegularProducts() throws Exception {
        order("""
                {"customerId":"C002","items":[{"productId":"P001","quantity":1},{"productId":"P003","quantity":1}]}
                """)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.originalSubtotal").value(25000))
                .andExpect(jsonPath("$.saleDiscountTotal").value(0))
                .andExpect(jsonPath("$.subtotalAfterSale").value(25000))
                .andExpect(jsonPath("$.membershipDiscount").value(2500))
                .andExpect(jsonPath("$.totalAmount").value(22500));
    }

    @Test
    void regularCustomerWithRegularAndSaleProducts() throws Exception {
        order("""
                {"customerId":"C001","items":[{"productId":"P001","quantity":1},{"productId":"P002","quantity":1}]}
                """)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.originalSubtotal").value(15000))
                .andExpect(jsonPath("$.saleDiscountTotal").value(1000))
                .andExpect(jsonPath("$.subtotalAfterSale").value(14000))
                .andExpect(jsonPath("$.membershipDiscount").value(0))
                .andExpect(jsonPath("$.totalAmount").value(14000));
    }
}
