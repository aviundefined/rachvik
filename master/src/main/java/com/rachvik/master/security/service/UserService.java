package com.rachvik.master.security.service;

import com.rachvik.master.security.entity.User;
import com.rachvik.master.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  public User loadUserByUsername(final String username) {
    return userRepository
        .findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
  }
}
