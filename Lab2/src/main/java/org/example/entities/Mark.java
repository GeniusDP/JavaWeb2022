package org.example.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class Mark {

  @Setter(AccessLevel.NONE)
  private Long id;

  private String name;

  public Mark(String name) {
    this.name = name;
  }
}
