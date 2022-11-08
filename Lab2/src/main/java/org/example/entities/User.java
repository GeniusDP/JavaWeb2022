package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {

  private Long id;

  private String username;

  private String password;

  private Role role;

  public User(String username, String password, Role role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }
}
