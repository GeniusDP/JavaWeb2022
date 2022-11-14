package org.example.repositories;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.entities.car.Car;
import org.example.entities.receipt.Receipt;
import org.example.entities.receipt.ReceiptStatus;
import org.example.entities.user.User;
import org.example.repositories.dao.cruddao.CrudReceiptDao;
import org.example.repositories.dao.specificdao.ReceiptSpecificDao;

@RequiredArgsConstructor
public class ReceiptRepository implements CrudRepository<Receipt, Long> {

  private final CrudReceiptDao crudReceiptDao;
  private final ReceiptSpecificDao receiptSpecificDao;
  private final CarRepository carRepository;
  private final UserRepository userRepository;

  public List<Receipt> getReceiptsOfUserByUserId(Long userId) {
    if (userId == null) {
      throw new IllegalArgumentException("user id must not be null");
    }
    return receiptSpecificDao.getReceiptsOfUserByUserId(userId);
  }

  @Override
  public Receipt insert(Receipt value) {
    return crudReceiptDao.insert(value);
  }

  @Override
  public int delete(Long key) {
    return crudReceiptDao.delete(key);
  }

  @Override
  public Receipt update(Long key, Receipt newValue) {
    return crudReceiptDao.findById(key);
  }

  @Override
  public List<Receipt> findAll() {
    List<Receipt> all = crudReceiptDao.findAll();

    all = all.stream().peek(receipt -> {
      Long carId = receipt.getCar().getId();
      Car car = carRepository.findById(carId);
      receipt.setCar(car);
      Long userId = receipt.getUser().getId();
      User user = userRepository.findById(userId);
      receipt.setUser(user);
    }).collect(Collectors.toList());

    return all;
  }

  @Override
  public Receipt findById(Long key) {
    Receipt receipt = crudReceiptDao.findById(key);
    if (receipt == null) {
      return null;
    }
    Long carId = receipt.getCar().getId();
    Car car = carRepository.findById(carId);
    receipt.setCar(car);
    Long userId = receipt.getUser().getId();
    User user = userRepository.findById(userId);
    receipt.setUser(user);
    return receipt;
  }

  public List<Car> getAllUnavailableCars() {
    List<Receipt> receipts = receiptSpecificDao.getRegisteredOrAcceptedReceipts();
    return receipts.stream().map(Receipt::getCar).distinct().collect(Collectors.toList());
  }

  public boolean setStatus(long receiptId, ReceiptStatus returned) {
    return receiptSpecificDao.setStatus(receiptId, returned);
  }

  public boolean declineReceipt(long receiptId, String message) {
    return receiptSpecificDao.declineReceipt(receiptId, message);
  }

  public boolean returnDamagedCar(long receiptId, int fixPrice) {
    Receipt receipt = findById(receiptId);
    if (receipt == null) {
      return false;
    }
    int resultPrice = receipt.getTotalPrice() + fixPrice;
    return receiptSpecificDao.returnDamagedCar(receiptId, resultPrice);
  }
}
