package org.example.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Random;
import org.assertj.core.api.Assertions;
import org.example.entities.user.Role;
import org.example.entities.user.User;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RegisterLoginServiceTest {

  private RegisterLoginService registerLoginService;

  @BeforeEach
  public void init() {

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    User user = User.builder()
      .id(1L)
      .firstName("user")
      .lastName("user")
      .password("user")
      .username("user")
      .banned(false)
      .role(Role.CLIENT)
      .build();

    User bannedUser = User.builder()
      .id(2L)
      .firstName("bannedUser")
      .lastName("bannedUser")
      .password("bannedUser")
      .username("bannedUser")
      .banned(true)
      .role(Role.CLIENT)
      .build();

    List<User> users = List.of(user, bannedUser);

    Mockito.doReturn(users).when(userRepository).findAll();

    Mockito.doAnswer(invocation -> {
      User userArgument = invocation.getArgument(0, User.class);
      userArgument.setId(Math.abs(new Random().nextLong()) % 1000 + 10);
      return userArgument;
    }).when(userRepository).insert(any(User.class));

    Mockito.doAnswer(invocation -> {
      String username = invocation.getArgument(0, String.class);
      return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }).when(userRepository).findByUsername(any(String.class));

    Mockito.doAnswer(invocation -> {
      String username = invocation.getArgument(0, String.class);
      return users.stream().anyMatch(u -> u.getUsername().equals(username));
    }).when(userRepository).existsByUsername(any(String.class));


    registerLoginService = new RegisterLoginService(userRepository);
  }

  @Test
  void login_Successful() {
    Assertions.assertThat(registerLoginService.login("user", "user")).isTrue();
  }

  @Test
  void loginFailed_WrongCredentials() {
    Assertions.assertThat(registerLoginService.login("user", "some_wrong_password")).isFalse();
  }

  @Test
  void loginFailed_BannedUser() {
    Assertions.assertThat(registerLoginService.login("bannedUser", "bannedUser")).isFalse();
  }

  @Test
  void registerUser_Successful() {
    User registeredUser = registerLoginService.registerUser(
      "newUser",
      "1234",
      "New",
      "User",
      Role.CLIENT
    );
    Assertions.assertThat(registeredUser).isNotNull();
  }

  @Test
  void registerUser_Failed_UserWithSuchUsernameExists() {
    User registeredUser = registerLoginService.registerUser(
      "user",
      "1234",
      "New",
      "User",
      Role.CLIENT
    );
    Assertions.assertThat(registeredUser).isNull();
  }

}