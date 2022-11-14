package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entities.user.Role;
import org.example.entities.user.User;
import org.example.exceptions.DatabaseException;
import org.example.repositories.UserRepository;
import org.example.security.SecurityContext;

import java.util.Objects;

@RequiredArgsConstructor
public class RegisterLoginService {

  private final UserRepository userRepository;

  public boolean login(String username, String password) {
    User user = userRepository.findByUsername(username);
    boolean loginResult = !(user == null) && Objects.equals(user.getPassword(), password);
    if (loginResult) {
      SecurityContext.getContext().setCurrentUser(user);
    }
    return loginResult;
  }

  public User registerUser(String username, String password, String firstName, String lastName, Role role) {
    try {
     User user = User.builder()
              .username(username)
              .password(password)
              .firstName(firstName)
              .lastName(lastName)
              .role(role)
              .build();
      return userRepository.insert(user);
    } catch (DatabaseException exception) {
      return null;
    }
  }
}
