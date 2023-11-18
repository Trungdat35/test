package com.example.demo.dto;

import com.example.demo.model.Decentralization;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SignUpRequest {
    private String userName;
    private String password;
    private String email;
    private Decentralization decentralization;
    private LocalDate createAt;
}
