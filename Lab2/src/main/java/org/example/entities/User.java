package org.example.entities;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
public class User {

  @Setter(AccessLevel.NONE)
  private Long id;

  private String username;

  private String password;

  private String firstName;

  private String lastName;

  private LocalDate dateOfBirth;

  private Role role;

  public User(String username, String password, String firstName, String lastName,
      LocalDate dateOfBirth, Role role) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.role = role;
  }
}
