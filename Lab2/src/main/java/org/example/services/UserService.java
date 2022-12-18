package org.example.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.entities.user.User;
import org.example.repositories.UserRepository;

@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User findByUsername(String username){
    return userRepository.findByUsername(username);
  }

  public boolean existsById(long id) {
    return userRepository.findById(id) != null;
  }

  public void banUser(long id) {
    userRepository.setIsBanned(id, true);
  }

  public void unbanUser(long id) {
    userRepository.setIsBanned(id, false);
  }

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }
}
