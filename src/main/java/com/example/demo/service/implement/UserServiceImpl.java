package com.example.demo.service.implement;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserRepo userRepo;
    public Users createUser(Users users){
        return userRepo.save(users);
    }
    public List<Users> getListUser(){
        return userRepo.findAll();
    }
}
