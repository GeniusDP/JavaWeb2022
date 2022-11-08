package org.example.repositories.dao.extractors;

import org.example.entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserExtractor implements Extractor<User> {

    @Override
    public User extract(ResultSet rs) throws SQLException {
        Long id = rs.getObject("id", Long.class);
        String username = rs.getString("username");
        String password = rs.getString("password");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String role = rs.getString("role");
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .role(Role.getRole(role))
                .build();
    }

}
