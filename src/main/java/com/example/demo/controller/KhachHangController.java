package com.example.demo.controller;

import com.example.demo.model.Products;
import com.example.demo.model.Users;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/khachhang")
@RequiredArgsConstructor
public class KhachHangController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hi khach hang");
    }
    @GetMapping(value = "/getlistuser")
    public List<Users> getlistUser() {
        return userService.getListUser();
    }
    @GetMapping(value = "/getlistproduct")
    public List<Products> getlistproduct(){
        return productService.getListP();
    }
}
