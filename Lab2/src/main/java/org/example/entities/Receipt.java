package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Receipt {

  private Long id;

  private User user;

  private Car car;

  private Boolean declined;

  private Boolean driverNeeded;

  private Integer daysNumber;

  private Integer totalPrice;

  public Receipt(User user, Car car, Boolean declined, Boolean driverNeeded, Integer daysNumber,
      Integer totalPrice) {
    this.user = user;
    this.car = car;
    this.declined = declined;
    this.driverNeeded = driverNeeded;
    this.daysNumber = daysNumber;
    this.totalPrice = totalPrice;
  }
}
