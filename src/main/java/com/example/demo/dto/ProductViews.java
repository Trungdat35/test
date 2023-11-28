package com.example.demo.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductViews {
    private int productID;
    private String nameProduct;
    private double price;
    private String avatarImageProduct;
    private String title;
    private int discount;
    private int numberOfViews;
}
