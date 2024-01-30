package com.example.Services.Auth.Jwt;

import com.example.Entities.User;
import com.example.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> opuser =  userRepository.findByEmail(email);
        if (opuser.isEmpty()){
            throw new UsernameNotFoundException("Email is not Found");
        } else {
            return new org.springframework.security.core.userdetails.User(opuser.get().getEmail(),opuser.get().getPassword(),new ArrayList<>());
        }
    }
}
