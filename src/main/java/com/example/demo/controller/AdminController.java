package com.example.demo.controller;

import com.example.demo.dto.UpdateStatusOrder;
import com.example.demo.exception.Response;
import com.example.demo.model.Account;
import com.example.demo.model.Products;
import com.example.demo.model.Users;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private ProductService productService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private final MailService mailService;

    @GetMapping(value = "/home")
    public String getListAccount() {
        return "Hi admin";
    }
    @PutMapping(value = "/capnhattrangthaidonhang", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updateStatusOrder(@RequestBody UpdateStatusOrder updateStatusOrder) {
        String status = adminService.updateStatusOrder(updateStatusOrder);
        if (updateStatusOrder.getOrderStatusID() == 2) {
            mailService.sendMail(updateStatusOrder.getEmail(), "Trạng thái đơn hàng", " - "+status);
        }
        if (updateStatusOrder.getOrderStatusID() == 3) {
            mailService.sendMail(updateStatusOrder.getEmail(), "Trạng thái đơn hàng", " - "+status);
        }
        if (updateStatusOrder.getOrderStatusID() == 4) {
            mailService.sendMail(updateStatusOrder.getEmail(), "Trạng thái đơn hàng", " - "+status);
        }
        if (updateStatusOrder.getOrderStatusID() == 5) {
            mailService.sendMail(updateStatusOrder.getEmail(), "Trạng thái đơn hàng", " - "+status);
        }
        return ResponseEntity.ok(new Response(HttpStatus.ACCEPTED, "Thông báo đã được gửi đến mail của bạn !"));
    }
}
