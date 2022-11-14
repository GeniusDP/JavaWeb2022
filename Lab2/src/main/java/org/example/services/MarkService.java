package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entities.car.Mark;
import org.example.repositories.MarkRepository;

@RequiredArgsConstructor
public class MarkService {

  private final MarkRepository markRepository;

  public boolean existsById(long markId) {
    return markRepository.findById(markId) != null;
  }

  public Mark findById(long markId) {
    return markRepository.findById(markId);
  }
}
