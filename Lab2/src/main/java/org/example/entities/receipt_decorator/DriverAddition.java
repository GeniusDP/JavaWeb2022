package org.example.entities.receipt_decorator;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DriverAddition extends AbstractReceiptEntity {
  private final AbstractReceiptEntity receiptEntity;
  private final Properties properties = new Properties();

  public DriverAddition(AbstractReceiptEntity receiptEntity) {
    super(receiptEntity.car, receiptEntity.user);
    this.receiptEntity = receiptEntity;
    try {
      properties.load(new FileReader("src/main/resources/application.properties"));
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public int getTotalPrice() {
    int price = 0;
    if(receiptEntity != null){
      price = receiptEntity.getTotalPrice() + Integer.parseInt(properties.getProperty("car.driver.cost"));
    }
    return price;
  }

}
