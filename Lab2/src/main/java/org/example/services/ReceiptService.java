package org.example.services;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.entities.car.Car;
import org.example.entities.receipt.Receipt;
import org.example.entities.receipt.ReceiptStatus;
import org.example.entities.user.User;
import org.example.repositories.ReceiptRepository;

@RequiredArgsConstructor
public class ReceiptService {

  private final ReceiptRepository receiptRepository;

  public void registerReceipt(Receipt receipt) {
    receiptRepository.insert(receipt);
  }

  public List<Receipt> getMyReceipts(User user) {
    return receiptRepository.getReceiptsOfUserByUserId(user.getId());
  }

  public boolean carIsAvailable(long carId) {
    List<Car> allUnavailableCars = receiptRepository.getAllUnavailableCars();
    Optional<Car> any = allUnavailableCars.stream().filter(car -> car.getId() == carId).findAny();
    return !any.isPresent();
  }

  public List<Receipt> getAllReceipts() {
    return receiptRepository.findAll();
  }

  public boolean returnCar(long receiptId) {
    System.out.println("returnCar");
    return receiptRepository.setStatus(receiptId, ReceiptStatus.RETURNED);
  }

  public boolean existsById(long receiptId) {
    return receiptRepository.findById(receiptId) != null;
  }

  public boolean acceptCar(long receiptId) {
    return receiptRepository.setStatus(receiptId, ReceiptStatus.ACCEPTED);
  }

  public boolean declineReceipt(long receiptId, String message) {
    return receiptRepository.declineReceipt(receiptId, message);
  }

  public boolean returnDamagedCar(long receiptId, int fixPrice) {
    System.out.println("returnDamagedCar");
    return receiptRepository.returnDamagedCar(receiptId, fixPrice);
  }
}
