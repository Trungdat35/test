package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService  userService;
    @RequestMapping(value = "/getlistuser",method = RequestMethod.GET)
    public List<Users> getListUser(){
        return userService.getListUser();
    }
}
