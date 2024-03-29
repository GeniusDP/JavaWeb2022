package org.example.entities.user;

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

  private String firstName;

  private String lastName;

  private Role role;

  private boolean banned;

  public User(String username, String password, String firstName, String lastName, Role role) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
  }
}
