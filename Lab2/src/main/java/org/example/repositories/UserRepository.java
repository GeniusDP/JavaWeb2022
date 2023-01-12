package org.example.repositories;

import java.util.List;
import lombok.AllArgsConstructor;
import org.example.entities.user.User;
import org.example.repositories.dao.cruddao.CrudUserDao;
import org.example.repositories.dao.specificdao.UserSpecificDao;

@AllArgsConstructor
public class UserRepository implements CrudRepository<User, Long> {

  private CrudUserDao userDao;
  private UserSpecificDao userSpecificDao;

  @Override
  public User insert(User value) {
    return userDao.insert(value);
  }

  @Override
  public int delete(Long key) {
    return userDao.delete(key);
  }

  @Override
  public User update(Long key, User newValue) {
    return userDao.update(key, newValue);
  }

  @Override
  public List<User> findAll() {
    return userDao.findAll();
  }

  @Override
  public User findById(Long key) {
    return userDao.findById(key);
  }

  public User findByUsername(String username) {
    return userSpecificDao.findByUsername(username);
  }

  public void setIsBanned(long id, boolean isBanned) {
    userSpecificDao.setIsBanned(id, isBanned);
  }

  public boolean existsByUsername(String username) {
    return findByUsername(username) != null;
  }
}
