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

  @Builder.Default
  private Boolean declined = false;

  private Boolean driverNeeded;

  private Integer daysNumber;

  private Integer totalPrice;

  @Builder.Default
  private Boolean fulfilled = false;

  public Receipt(User user, Car car, Boolean declined, Boolean driverNeeded, Integer daysNumber,
      Integer totalPrice, Boolean fulfilled) {
    this.user = user;
    this.car = car;
    this.declined = declined;
    this.driverNeeded = driverNeeded;
    this.daysNumber = daysNumber;
    this.totalPrice = totalPrice;
    this.fulfilled = fulfilled;
  }

  @Override
  public String toString() {
    return String.format("""
              Here is your Receipt.
              first name  : %s,
              last name   : %s,
              car name    : %s,
              car model   : %s,
              for days    : %d,
              driver need : %b,
              total price : %d$
            """, user.getFirstName(), user.getLastName(), car.getName(), car.getMark().getName(),
        daysNumber, driverNeeded, totalPrice );
  }
}
