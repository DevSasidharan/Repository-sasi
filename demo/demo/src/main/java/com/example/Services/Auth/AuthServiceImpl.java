package com.example.Services.Auth;

import com.example.Dtos.SignupRequest;
import com.example.Dtos.UserDto;
import com.example.Entities.User;
import com.example.Enums.UserRole;
import com.example.Repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    @Autowired
    private final UserRepository userRepository;

    @PostConstruct
    public void adminaccount(){
        User adminacc =  userRepository.findByUserRole(UserRole.ADMIN);
        if (adminacc == null){
            User user = new User();
            user.setName("admin");
            user.setEmail("admin@gmail.com");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
        }
    }
    @Override
    public UserDto createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        User created =  userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setId(created.getId());
        userDto.setName(created.getName());
        userDto.setEmail(created.getEmail());
        userDto.setUserRole(created.getUserRole());
        return userDto;
    }
}
