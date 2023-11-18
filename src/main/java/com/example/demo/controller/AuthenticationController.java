package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.exception.Response;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final MailService mailService;

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> signUp(@RequestBody SignUpRequest signUpRequest) {
        authenticationService.signUp(signUpRequest);
        mailService.sendMail(signUpRequest.getEmail(), "Đăng ký tài khoản", "Thành công");
        return ResponseEntity.ok(new Response(HttpStatus.ACCEPTED, "Thông báo đã được gửi đến mail của bạn !"));
    }

    @PostMapping(value = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthenticationRespose> signIn(@RequestBody SignInRequest signinRequest) {
        return ResponseEntity.ok(authenticationService.signIn(signinRequest));
    }

    @PostMapping(value = "/refreshtoken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthenticationRespose> signIn(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @PutMapping(value = "/changepassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> signIn(@RequestBody ChangePasswordRequest changePasswordRequest,
                                           Principal connectedAccount) {
        authenticationService.changePassword(changePasswordRequest, connectedAccount);
        return ResponseEntity.ok(new Response(HttpStatus.ACCEPTED, "Change password successful !"));
    }
    @PostMapping(value = "/regenerateotp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> regenerateOTP(@RequestParam String email) {
         authenticationService.regenerateOTP(email);
         return ResponseEntity.ok(new Response(HttpStatus.ACCEPTED, "Mã OTP đã được gửi đến mail của bạn"));
    }
    @PostMapping(value = "/verifyaccount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> verifyAccount(@RequestParam String email, @RequestParam  String otp) {
        authenticationService.verifyAccount(email,otp);
        return ResponseEntity.ok(new Response(HttpStatus.ACCEPTED, "Xác thực thành công !"));
    }
}
