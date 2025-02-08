package com.unitedGo.service.impl;

import com.unitedGo.entity.Users;
import com.unitedGo.model.Token;
import com.unitedGo.model.UserInfo;
import com.unitedGo.repository.UserRepository;
import com.unitedGo.service.JWTService;
import com.unitedGo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;


    public UsersServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Users registerUser(UserInfo userInfo) {
    Optional<Users> userExists = userRepository.findByUserName(userInfo.getUserName());
    if (userExists.isPresent()) {
        throw new RuntimeException("User already exists with userName: " + userInfo.getUserName());
    }
        Users user = new Users();
        user.setUserName(userInfo.getUserName());
        user.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Token loginUser(UserInfo userInfo) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userInfo.getUserName(), userInfo.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        boolean loginStatus = authenticate.isAuthenticated();
        if (!loginStatus) {
            throw new BadCredentialsException("Invalid username or password!");
        }
        String authToken = jwtService.generateToken(userInfo.getUserName());
        return new Token(authToken);
    }


}
