package com.rachvik.user.mappers;

import com.rachvik.user.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

  public Role protoToModel(final com.rachvik.profile.user.model.Role role) {
    if (role == null) {
      return Role.USER;
    }
    return switch (role) {
      case ROLE_ADMIN -> Role.ADMIN;
      case ROLE_USER -> Role.USER;
      default -> throw new RuntimeException("Unsupported role: " + role);
    };
  }

  public com.rachvik.profile.user.model.Role modelToProto(final Role role) {
    if (role == null) {
      return com.rachvik.profile.user.model.Role.ROLE_USER;
    }
    return switch (role) {
      case ADMIN -> com.rachvik.profile.user.model.Role.ROLE_ADMIN;
      case USER -> com.rachvik.profile.user.model.Role.ROLE_USER;
    };
  }
}
