package com.example.demo.service.implement;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepo;
import com.example.demo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private final AccountRepo accountRepo;
    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return accountRepo.findByUserName(username).orElseThrow(()-> new UsernameNotFoundException("Account not found"));
            }
        };
    }
    public List<Account> getListAccount() {
        return accountRepo.findAll();
    }

}
