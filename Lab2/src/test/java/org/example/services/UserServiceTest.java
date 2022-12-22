package org.example.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;
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
class UserServiceTest {

  private UserService userService;

  @BeforeEach
  private void init() {
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

    Mockito.doAnswer(invocation -> {
      Long id = invocation.getArgument(0, Long.class);
      return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }).when(userRepository).findById(any(Long.class));


    Mockito.doAnswer(invocation -> {
      Long id = invocation.getArgument(0, Long.class);
      Boolean isBanned = invocation.getArgument(1, Boolean.class);
      Optional<User> withId = users.stream().filter(u -> u.getId().equals(id)).findFirst();
      withId.ifPresent(value -> value.setBanned(isBanned));
      return null;
    }).when(userRepository).setIsBanned(any(Long.class), any(Boolean.class));

    userService = new UserService(userRepository);
  }

  @Test
  void findByUsername() {
    User user = userService.findByUsername("user");
    Assertions.assertThat(user).isNotNull();
    Assertions.assertThat(user.getUsername()).isEqualTo("user");

    User absentUser = userService.findByUsername("absentUser");
    Assertions.assertThat(absentUser).isNull();
  }

  @Test
  void existsById() {
    Assertions.assertThat(userService.existsById(1L)).isTrue();
    Assertions.assertThat(userService.existsById(2L)).isTrue();

    Assertions.assertThat(userService.existsById(1000L)).isFalse();
  }

  @Test
  void findAllUsers() {
    Assertions.assertThat(userService.findAllUsers().size()).isEqualTo(2);
  }

  @Test
  void banUser() {
    userService.banUser(1L);
    Assertions.assertThat(userService.findByUsername("user").isBanned()).isTrue();
  }

  @Test
  void unbanUser() {
    userService.unbanUser(2L);
    Assertions.assertThat(userService.findByUsername("bannedUser").isBanned()).isFalse();
  }

}