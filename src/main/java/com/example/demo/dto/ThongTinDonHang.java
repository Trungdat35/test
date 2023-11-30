package com.example.demo.dto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongTinDonHang {

    private int maDonHang;
    private String nameProduct;
    private int quantity;
    private LocalDate ngayMua;
    private String paymentMethod;
    private String statusName;
    private double priceTotal;



}
