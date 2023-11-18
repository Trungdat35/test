package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class AccountController {
    @Autowired
    private  AccountService accountService;
    @GetMapping(value = "/getlistaccount")
    public List<Account> get() {
        return accountService.getListAccount();
    }
}
