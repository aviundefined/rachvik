package com.rachvik.master.security.service;

import com.rachvik.clients.ProfileServiceGrpcClient;
import com.rachvik.master.models.Role;
import com.rachvik.master.models.User;
import com.rachvik.profile.user.model.GetUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final ProfileServiceGrpcClient profileServiceGrpcClient;

  public User loadUserByUsername(final String username) {
    val response =
        profileServiceGrpcClient
            .getStub()
            .getUser(GetUserRequest.newBuilder().setUsername(username).build());
    if (!response.hasUser()) {
      throw new UsernameNotFoundException("User not found: " + username);
    }
    val savedUser = response.getUser();
    return User.builder()
        .email(savedUser.getUsername())
        .firstname(savedUser.getFirstName())
        .lastname(savedUser.getLastName())
        .password(savedUser.getPassword())
        .role(mapRole(savedUser.getRole()))
        .enabled(savedUser.getEnabled())
        .credentialsNonExpired(savedUser.getCredentialsNonExpired())
        .accountNonLocked(savedUser.getAccountNonExpired())
        .accountNonExpired(savedUser.getAccountNonExpired())
        .build();
  }

  private Role mapRole(com.rachvik.profile.user.model.Role role) {
    return switch (role) {
      case ROLE_USER -> Role.USER;
      case ROLE_ADMIN -> Role.ADMIN;
      default -> throw new RuntimeException("Unsupported role: " + role);
    };
  }
}
