package org.example.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.example.entities.car.Mark;
import org.example.repositories.MarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MarkServiceTest {

  private MarkService markService;

  @BeforeEach
  public void init() {

    Mark lexus = new Mark(1L, "Lexus");
    Mark bmw = new Mark(2L, "BMW");
    Mark ford = new Mark(3L, "Ford");

    List<Mark> marks = List.of(lexus, bmw, ford);

    MarkRepository markRepository = Mockito.mock(MarkRepository.class);

    Mockito.doAnswer(invocation -> {
      Long id = invocation.getArgument(0, Long.class);
      return marks.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }).when(markRepository).findById(any(Long.class));

    Mockito.doAnswer(invocation -> {
      String name = invocation.getArgument(0, String.class);
      return marks.stream().filter(m -> m.getName().equals(name)).findFirst().orElse(null);
    }).when(markRepository).findByName(any(String.class));

    markService = new MarkService(markRepository);
  }

  @Test
  void existsById() {
    Assertions.assertThat(markService.existsById(1L)).isTrue();
    Assertions.assertThat(markService.existsById(2L)).isTrue();
    Assertions.assertThat(markService.existsById(3L)).isTrue();
    Assertions.assertThat(markService.existsById(100L)).isFalse();
    Assertions.assertThat(markService.existsById(37L)).isFalse();
  }

  @Test
  void findById() {
    Assertions.assertThat(markService.findById(1L).getName()).isEqualTo("Lexus");
    Assertions.assertThat(markService.findById(2L).getName()).isEqualTo("BMW");
    Assertions.assertThat(markService.findById(3L).getName()).isEqualTo("Ford");
    Assertions.assertThat(markService.findById(100L)).isNull();
    Assertions.assertThat(markService.findById(37L)).isNull();
  }
}