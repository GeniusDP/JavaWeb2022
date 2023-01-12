package org.example.entities.receipt_decorator;

import org.example.entities.car.Car;
import org.example.entities.user.User;

public abstract class AbstractReceiptEntity {

  protected Car car;
  protected User user;

  public AbstractReceiptEntity(Car car, User user) {
    if(car == null || user == null){
      throw new IllegalArgumentException("car and user must not be null!");
    }
    this.car = car;
    this.user = user;
  }

  public abstract int getTotalPrice();

}
