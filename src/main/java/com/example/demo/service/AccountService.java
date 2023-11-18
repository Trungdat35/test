package com.example.demo.service;

import com.example.demo.model.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService {
    UserDetailsService userDetailsService();
    List<Account> getListAccount();
}
