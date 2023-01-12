package org.example.entities.car;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Car {

  private Long id;

  private Mark mark;

  private QualityClass qualityClass;

  private String name;

  private Integer basePrice;


  public static CarBuilder builder() {
    return new CarBuilder();
  }

  public static class CarBuilder {
    
    private Long id;

    private Mark mark;

    private QualityClass qualityClass;

    private String name;

    private Integer basePrice;


    public CarBuilder id(Long id){
      this.id = id;
      return this;
    }

    public CarBuilder mark(Mark mark){
      this.mark = mark;
      return this;
    }

    public CarBuilder qualityClass(QualityClass qualityClass){
      this.qualityClass = qualityClass;
      return this;
    }

    public CarBuilder name(String name){
      this.name = name;
      return this;
    }


    public CarBuilder basePrice(int basePrice){
      this.basePrice = basePrice;
      return this;
    }
    
    public Car build(){
      return new Car(id, mark, qualityClass, name, basePrice);
    }

  }


  @Override
  public String toString() {
    return String.format("Car(id=%3s, mark=%30s, qualityClass=%10s, name=%15s, basePrice=%10s)",
        id, mark, qualityClass, name, basePrice);
  }
}
