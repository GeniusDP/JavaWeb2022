package org.example.entities.car;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mark {

  private Long id;

  private String name;

  public Mark(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format("Mark(id=%3s, name=%10s)", id, name);
  }

}
