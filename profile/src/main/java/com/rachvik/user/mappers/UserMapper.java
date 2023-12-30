package com.rachvik.user.mappers;

import com.rachvik.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final RoleMapper roleMapper;

  public User protoToModel(final com.rachvik.profile.user.model.User user) {
    return User.builder()
        .username(user.getUsername())
        .firstname(user.getFirstName())
        .lastname(user.getLastName())
        .password(user.getPassword())
        .role(roleMapper.protoToModel(user.getRole()))
        .enabled(user.getEnabled())
        .accountNonExpired(user.getAccountNonExpired())
        .credentialsNonExpired(user.getCredentialsNonExpired())
        .accountNonLocked(user.getAccountNonLocked())
        .build();
  }

  public com.rachvik.profile.user.model.User modelToProto(final User user) {
    return com.rachvik.profile.user.model.User.newBuilder()
        .setUsername(user.getUsername())
        .setFirstName(user.getFirstname())
        .setLastName(user.getLastname())
        .setPassword(user.getPassword())
        .setRole(roleMapper.modelToProto(user.getRole()))
        .setEnabled(user.isEnabled())
        .setAccountNonExpired(user.isAccountNonExpired())
        .setCredentialsNonExpired(user.isCredentialsNonExpired())
        .setAccountNonLocked(user.isAccountNonExpired())
        .build();
  }
}
