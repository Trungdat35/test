package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderRequest {
    private int userID;
    private int productID;
    private int quantity;
    private int orderStatus;
    private double priceTotal;
    private int paymentID;
    private LocalDate createAt;
}
