package com.example.demo.dto;

import lombok.Data;

@Data
public class CancelOrderRequest {
    private int userID;
    private int orderID;
}
