package com.unitedGo.security;

import com.unitedGo.entity.UserPrincipal;
import com.unitedGo.entity.Users;
import com.unitedGo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<Users> user = Optional.ofNullable(userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
        return new UserPrincipal(user.get());
    }
}
