package com.rachvik.master.security.service;

import com.rachvik.clients.ProfileServiceGrpcClient;
import com.rachvik.master.models.AuthenticationRequest;
import com.rachvik.master.models.AuthenticationResponse;
import com.rachvik.master.models.SignupRequest;
import com.rachvik.profile.user.model.GetUserRequest;
import com.rachvik.profile.user.model.Role;
import com.rachvik.profile.user.model.SaveUserRequest;
import com.rachvik.profile.user.model.User;
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
  private final ProfileServiceGrpcClient profileServiceGrpcClient;
  private final JWTService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse signup(final SignupRequest request) {
    val user =
        User.newBuilder()
            .setUsername(request.getEmail())
            .setFirstName(request.getFirstname())
            .setLastName(request.getLastname())
            .setRole(request.getRole() == null ? Role.ROLE_USER : mapRole(request.getRole()))
            .setEnabled(true)
            .setAccountNonExpired(true)
            .setAccountNonLocked(true)
            .setCredentialsNonExpired(true)
            .setPassword(passwordEncoder.encode(request.getPassword()))
            .build();
    val saveUserResponse =
        profileServiceGrpcClient
            .getStub()
            .saveUser(SaveUserRequest.newBuilder().setUser(user).build());
    return AuthenticationResponse.builder().token(jwtService.generateToken(user)).build();
  }

  private Role mapRole(com.rachvik.master.models.Role role) {
    return switch (role) {
      case USER -> Role.ROLE_USER;
      case ADMIN -> Role.ROLE_ADMIN;
    };
  }

  public AuthenticationResponse login(final AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    val response =
        profileServiceGrpcClient
            .getStub()
            .getUser(GetUserRequest.newBuilder().setUsername(request.getUsername()).build());
    if (!response.hasUser()) {
      throw new UsernameNotFoundException("User not found: " + request.getUsername());
    }
    val user = response.getUser();
    return AuthenticationResponse.builder().token(jwtService.generateToken(user)).build();
  }
}
