package com.rachvik.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
    name = "_user",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String userId;
  private String username;
  private String firstname;
  private String lastname;
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;
}
