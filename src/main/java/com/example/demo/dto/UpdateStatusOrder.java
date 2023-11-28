package com.example.demo.dto;

import lombok.Data;

@Data
public class UpdateStatusOrder {
    private int orderID;
    private int orderStatusID;
    private String email;
}
