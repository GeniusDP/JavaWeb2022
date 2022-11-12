package org.example.entities.receipt;

public class DriverAddition extends AbstractReceiptEntity {
  private final static int DRIVER_ADDITION_PRICE = 500;
  private final AbstractReceiptEntity receiptEntity;

  public DriverAddition(AbstractReceiptEntity receiptEntity) {
    super(receiptEntity.car, receiptEntity.user);
    this.receiptEntity = receiptEntity;
  }

  @Override
  public int getTotalPrice() {
    return receiptEntity == null ? 0 : receiptEntity.getTotalPrice() + DRIVER_ADDITION_PRICE;
  }

}
