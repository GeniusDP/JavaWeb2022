package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entities.Role;
import org.example.entities.User;
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
        if(loginResult){
            SecurityContext.getContext().setCurrentUser(user);
        }
        return loginResult;
    }

    public boolean registerClient(String username, String password, String firstName, String lastName) {
        try {
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .firstName(firstName)
                    .lastName(lastName)
                    .role(Role.CLIENT)
                    .build();
            userRepository.insert(user);
            SecurityContext.getContext().setCurrentUser(user);
        } catch (DatabaseException exception){
            return false;
        }
        return true;
    }
}
