package com.unitedGo.controller;

import com.unitedGo.entity.Users;
import com.unitedGo.model.Token;
import com.unitedGo.model.UserInfo;
import com.unitedGo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class UsersController {

    private final UsersService usersService;
    @Autowired



    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody UserInfo userInfo) {
      Users users = usersService.registerUser(userInfo);
      return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Token> loginUser(@RequestBody UserInfo userInfo) {
        Token loginResponse = usersService.loginUser(userInfo);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

}
