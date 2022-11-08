package org.example.repositories;

import lombok.AllArgsConstructor;
import org.example.entities.User;
import org.example.repositories.dao.cruddao.CrudUserDao;
import org.example.repositories.dao.specificdao.UserSpecificDao;

import java.util.List;

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

    public User findByUsername(String username){
        return userSpecificDao.findByUsername(username);
    }

}
