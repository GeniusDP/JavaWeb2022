package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.repositories.UserRepository;

@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;


  public boolean existsById(long id) {
    return userRepository.findById(id) != null;
  }

  public void banUser(long id) {
    userRepository.setIsBanned(id, true);
  }

  public void unbanUser(long id) {
    userRepository.setIsBanned(id, false);
  }
}
