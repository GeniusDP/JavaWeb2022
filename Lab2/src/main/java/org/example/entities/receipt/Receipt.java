package org.example.entities.receipt;

public class Receipt extends AbstractReceiptEntity {

  private final AbstractReceiptEntity receiptPriceBuilder;

  public Receipt(AbstractReceiptEntity receiptPriceBuilder) {
    super(receiptPriceBuilder.car, receiptPriceBuilder.user);
    this.receiptPriceBuilder = receiptPriceBuilder;
  }

  @Override
  public int getTotalPrice() {
    return receiptPriceBuilder.getTotalPrice();
  }

}
