package com.example.Controllers;

import com.example.Dtos.AuthenticationRequest;
import com.example.Dtos.AuthenticationResponse;
import com.example.Dtos.SignupRequest;
import com.example.Dtos.UserDto;
import com.example.Entities.User;
import com.example.Repositories.UserRepository;
import com.example.Services.Auth.AuthService;
import com.example.Services.Auth.Jwt.UserDetailsServiceImpl;
import com.example.Utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private final AuthService authService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> signupuser(@RequestBody SignupRequest signupRequest){
        UserDto userDto = authService.createUser(signupRequest);

        if (userDto == null){
            return new ResponseEntity<>("User not created", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
        }
    }

    @PostMapping("/signin")
    public AuthenticationResponse authenticationResponseToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException {
        try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        } catch (DisabledException e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        final UserDetails userDetails =  userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtils.generateToken(userDetails.getUsername());
        Optional<User> opuser = userRepository.findByEmail(userDetails.getUsername());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (opuser.isPresent()){
            authenticationResponse.setUserId(opuser.get().getId());
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(opuser.get().getUserRole());
        }
        return authenticationResponse;
    }
}

