package org.example.entities.receipt_decorator;

import org.example.entities.car.Car;
import org.example.entities.user.User;

public class BasicReceipt extends AbstractReceiptEntity {

  public BasicReceipt(Car car, User user) {
    super(car, user);
  }

  @Override
  public int getTotalPrice() {
    return car.getBasePrice();
  }
}
