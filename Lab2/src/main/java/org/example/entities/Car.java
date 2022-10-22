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

}
