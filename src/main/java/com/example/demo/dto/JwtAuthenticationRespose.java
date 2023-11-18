package com.example.demo.dto;

import lombok.Data;

@Data
public class JwtAuthenticationRespose {
    private  String token;
    private  String refreshToken;
}
