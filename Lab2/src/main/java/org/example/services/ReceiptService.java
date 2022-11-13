package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entities.Receipt;
import org.example.repositories.ReceiptRepository;

@RequiredArgsConstructor
public class ReceiptService {

  private final ReceiptRepository receiptRepository;

  public void registerReceipt(Receipt receipt) {
    receiptRepository.insert(receipt);
  }

}
