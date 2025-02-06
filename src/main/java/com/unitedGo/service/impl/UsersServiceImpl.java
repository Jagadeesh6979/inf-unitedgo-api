package com.unitedGo.service.impl;

import com.unitedGo.entity.Users;
import com.unitedGo.model.UserInfo;
import com.unitedGo.repository.UserRepository;
import com.unitedGo.service.UsersService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UsersServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Users registerUser(UserInfo userInfo) {
    Optional<Users> userExists = userRepository.findByUserName(userInfo.getUserName());
    if (userExists.isPresent()) {
        throw new RuntimeException("User alreday exists with userName: " + userInfo.getUserName());
    }
        Users user = new Users();
        user.setUserName(userInfo.getUserName());
        user.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        return userRepository.save(user);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Users user =  userRepository.findByUserName(username);
//        if (user == null) {
//            System.out.println("User Not Found");
//            throw new UsernameNotFoundException("user not found");
//        }
//        return new User(user.getUserName(),user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
//    }

}
