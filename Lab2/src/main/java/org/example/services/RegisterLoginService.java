package org.example.services;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.example.entities.user.Role;
import org.example.entities.user.User;
import org.example.exceptions.DatabaseException;
import org.example.repositories.UserRepository;

@RequiredArgsConstructor
public class RegisterLoginService {

  private final UserRepository userRepository;

  public boolean login(String username, String password) {
    User user = userRepository.findByUsername(username);
    return !(user == null) && (!user.isBanned()) && Objects.equals(user.getPassword(), password);
  }

  public User registerUser(String username, String password, String firstName, String lastName, Role role) {
    try {
      if (!userRepository.existsByUsername(username)) {
        User user = User.builder()
          .username(username)
          .password(password)
          .firstName(firstName)
          .lastName(lastName)
          .role(role)
          .build();
        return userRepository.insert(user);
      }
      return null;
    } catch (DatabaseException exception) {
      return null;
    }
  }
}
