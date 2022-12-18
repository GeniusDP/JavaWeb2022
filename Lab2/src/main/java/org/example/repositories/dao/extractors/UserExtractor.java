package org.example.repositories.dao.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.entities.user.Role;
import org.example.entities.user.User;

public class UserExtractor implements Extractor<User> {

    @Override
    public User extract(ResultSet rs) throws SQLException {
        Long id = rs.getObject("id", Long.class);
        String username = rs.getString("username");
        String password = rs.getString("password");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String role = rs.getString("role");
        Boolean isBanned = rs.getObject("is_banned", Boolean.class);
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .banned(isBanned)
                .role(Role.getRole(role))
                .build();
    }

}
