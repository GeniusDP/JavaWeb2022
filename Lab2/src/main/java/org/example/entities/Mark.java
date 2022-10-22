package org.example.entities;

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

}
