package com.unitedGo.controller;

import com.unitedGo.entity.Users;
import com.unitedGo.model.UserInfo;
import com.unitedGo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("auth")
public class UsersController {

    private final UsersService usersService;
    @Autowired
    private AuthenticationManager authenticationManager;


    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody UserInfo userInfo) {
      Users users = usersService.registerUser(userInfo);
      return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserInfo userInfo) {
        String message = "";
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userInfo.getUserName(), userInfo.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        System.out.println("Authenticate object: " + authenticate);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        System.out.println("Check: " + SecurityContextHolder.getContext().getAuthentication());
        boolean loginStatus = authenticate.isAuthenticated();
        System.out.println(loginStatus);

        if (!loginStatus) {
            throw new BadCredentialsException("Invalid username or password!");
        }
        return new ResponseEntity<>("You have successfully logged in!", HttpStatus.OK);
    }

}
