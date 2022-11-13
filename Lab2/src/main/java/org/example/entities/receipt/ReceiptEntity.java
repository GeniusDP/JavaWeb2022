package org.example.entities.receipt;

public class ReceiptEntity extends AbstractReceiptEntity {

  private final AbstractReceiptEntity receiptPriceBuilder;

  public ReceiptEntity(AbstractReceiptEntity receiptPriceBuilder) {
    super(receiptPriceBuilder.car, receiptPriceBuilder.user);
    this.receiptPriceBuilder = receiptPriceBuilder;
  }

  @Override
  public int getTotalPrice() {
    return receiptPriceBuilder.getTotalPrice();
  }

}
