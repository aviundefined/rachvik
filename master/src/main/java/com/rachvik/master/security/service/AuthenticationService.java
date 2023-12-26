package com.rachvik.master.security.service;

import com.rachvik.master.models.AuthenticationRequest;
import com.rachvik.master.models.AuthenticationResponse;
import com.rachvik.master.models.SignupRequest;
import com.rachvik.master.security.entity.Role;
import com.rachvik.master.security.entity.User;
import com.rachvik.master.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final JWTService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse signup(final SignupRequest request) {
    val user =
        User.builder()
            .email(request.getEmail())
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .role(request.getRole() == null ? Role.USER : request.getRole())
            .enabled(true)
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .password(passwordEncoder.encode(request.getPassword()))
            .build();
    userRepository.save(user);
    return AuthenticationResponse.builder().token(jwtService.generateToken(user)).build();
  }

  public AuthenticationResponse login(final AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    val user =
        userRepository
            .findByEmail(request.getUsername())
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found: " + request.getUsername()));
    return AuthenticationResponse.builder().token(jwtService.generateToken(user)).build();
  }
}
