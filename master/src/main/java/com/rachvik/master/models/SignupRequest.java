package com.rachvik.master.models;

import com.rachvik.master.security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {
  private String firstname;
  private String lastname;
  private String password;
  private String email;
  private Role role = Role.USER;
}
