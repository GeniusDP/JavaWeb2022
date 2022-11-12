package org.example.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Car {

  private Long id;

  private Mark mark;

  private QualityClass qualityClass;

  private String name;

  private Integer basePrice;

  @Override
  public String toString() {
    return String.format("Car(id=%3s, mark=%30s, qualityClass=%10s, name=%15s, basePrice=%10s)",
            id, mark, qualityClass, name, basePrice);
  }
}
