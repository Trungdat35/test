package com.example.demo.service.implement;

import com.example.demo.dto.*;
import com.example.demo.exception.AllException;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepo;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JwtService;
import com.example.demo.util.EmailUtil;
import com.example.demo.util.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private OtpUtil otpUtil;
    @Autowired
    private EmailUtil emailUtil;

    public Account signUp(SignUpRequest signUpRequest) {
        Account account = new Account();
        account.setUserName(signUpRequest.getUserName());
        account.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        account.setDecentralization(signUpRequest.getDecentralization());
        account.setCreateAt(signUpRequest.getCreateAt());
        account.setEmail(signUpRequest.getEmail());
        account.setStatus(1);
        return accountRepo.save(account);
    }

    public JwtAuthenticationRespose signIn(SignInRequest signinRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUserName(), signinRequest.getPassword()));
        Account account = accountRepo.findByUserName(signinRequest.getUserName()).orElseThrow(() ->
                new IllegalArgumentException("Invail username or password"));
        if (account.getStatus() != 1) {
            throw new AllException("Tài khoản đã ngưng hoạt động");
        }
        String jwt = jwtService.generateToken(account);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), account);
        JwtAuthenticationRespose jwtAuthenticationRespose = new JwtAuthenticationRespose();
        jwtAuthenticationRespose.setToken(jwt);
        jwtAuthenticationRespose.setRefreshToken(refreshToken);
        return jwtAuthenticationRespose;
    }

    public JwtAuthenticationRespose refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userName = jwtService.extractUserName(refreshTokenRequest.getToken());
        Account account = accountRepo.findByUserName(userName).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), account)) {
            var jwt = jwtService.generateToken(account);
            JwtAuthenticationRespose jwtAuthenticationRespose = new JwtAuthenticationRespose();
            jwtAuthenticationRespose.setToken(jwt);
            jwtAuthenticationRespose.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationRespose;
        }
        return null;
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest, Principal principal) {
        Optional<Account> optionalAccount = accountRepo.findByEmail(changePasswordRequest.getEmail());
        if (changePasswordRequest.getNewPassWord().equals(changePasswordRequest.getConfirmationPassword())) {
            optionalAccount.get().setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassWord()));
            optionalAccount.get().setResetPasswordToken(passwordEncoder.encode(changePasswordRequest.getNewPassWord()));
            optionalAccount.get().setUpdateAt(LocalDate.now());
            accountRepo.save(optionalAccount.get());
        } else {
            throw new AllException("Password are not same !");
        }
    }

    public void verifyAccount(String email, String otp) {
        Optional<Account> optionalAccount = accountRepo.findByEmail(email);
        if (optionalAccount.isPresent()) {
            if (optionalAccount.get().getResetPasswordToken().equals(otp) &&
                    Duration.between(optionalAccount.get().getResetPasswordTokenExprixy(), LocalDateTime.now()).getSeconds() < 60) {
            } else {
                throw new AllException("OTP sai hoặc đã hết hạn !");
            }
        } else {
            throw new AllException("Account not found !");
        }

    }

    public void regenerateOTP(String email) {
        Optional<Account> optionalAccount = accountRepo.findByEmail(email);
        if (optionalAccount.isPresent()) {
            String otp = otpUtil.generateOtp();
            try {
                emailUtil.sendOtpEmail(email, otp);
            } catch (MessagingException e) {
                throw new AllException("Unable to send otp please try again");
            }
            optionalAccount.get().setResetPasswordToken(otp);
            optionalAccount.get().setResetPasswordTokenExprixy(LocalDateTime.now());
            accountRepo.save(optionalAccount.get());
        } else {
            throw new AllException("Account not found !");
        }
    }

  public Account getAccountByID(int accID){
        Optional<Account> optionalAccount = accountRepo.findById(accID);
        if(optionalAccount.isPresent())  return optionalAccount.get();
        return null;
  }
    public List<Account> getListAccount() {
        return accountRepo.findAll();
    }
}
