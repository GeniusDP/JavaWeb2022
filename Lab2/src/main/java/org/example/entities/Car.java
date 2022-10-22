package org.example.entities;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class Car {

  @Setter(AccessLevel.NONE)
  private Long id;

  private Mark mark;

  private QualityClass qualityClass;

  private String name;

  private Integer basePrice;

  public Car(Mark mark, QualityClass qualityClass, String name, Integer basePrice) {
    this.mark = mark;
    this.qualityClass = qualityClass;
    this.name = name;
    this.basePrice = basePrice;
  }
}
