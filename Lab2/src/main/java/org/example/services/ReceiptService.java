package org.example.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.entities.Receipt;
import org.example.entities.User;
import org.example.repositories.ReceiptRepository;

@RequiredArgsConstructor
public class ReceiptService {

  private final ReceiptRepository receiptRepository;

  public void registerReceipt(Receipt receipt) {
    receiptRepository.insert(receipt);
  }

  public List<Receipt> getAllMyReceipts(User user) {
    return receiptRepository.getReceiptsOfUserByUserId(user.getId());
  }
}
