package org.example.repositories.dao.specificdao;

import org.example.entities.User;

public interface UserSpecificDao {

    User findByUsername(String username);

}
