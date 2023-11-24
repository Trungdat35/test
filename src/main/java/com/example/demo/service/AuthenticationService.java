package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.model.Account;

import java.security.Principal;
import java.util.List;

public interface AuthenticationService {
    Account signUp(SignUpRequest signUpRequest);

    JwtAuthenticationRespose signIn(SignInRequest signinRequest);

    JwtAuthenticationRespose refreshToken(RefreshTokenRequest refreshTokenRequest);

    void changePassword(ChangePasswordRequest changePasswordRequest, Principal principal);

    void regenerateOTP(String email);

    void verifyAccount(String email, String otp);
    Account getAccountByID(int accID);
    List<Account> getListAccount();
}
