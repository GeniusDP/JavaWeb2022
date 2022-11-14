package org.example.repositories.dao.specificdao;

import org.example.entities.user.User;

public interface UserSpecificDao {

  User findByUsername(String username);

  void setIsBanned(Long id, boolean isBanned);
}
