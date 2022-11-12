package org.example.entities.receipt;

public class DaysRentAddition extends AbstractReceiptEntity {

  private final AbstractReceiptEntity receiptEntity;
  private final int daysCount;

  public DaysRentAddition(AbstractReceiptEntity receiptEntity, int daysCount) {
    super(receiptEntity.car, receiptEntity.user);
    this.receiptEntity = receiptEntity;
    this.daysCount = daysCount;
  }


  @Override
  public int getTotalPrice() {
    return receiptEntity == null ? 0 : (receiptEntity.getTotalPrice() * daysCount);
  }

}
