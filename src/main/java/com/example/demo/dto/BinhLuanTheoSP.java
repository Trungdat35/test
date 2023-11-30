package com.example.demo.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinhLuanTheoSP {
    private String productName;

    private String contentRated;

    private int point_evaluation;

    private String contentSeen;

    private LocalDate createAt;
}
