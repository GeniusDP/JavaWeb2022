package org.example.repositories;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.entities.Receipt;
import org.example.repositories.dao.cruddao.CrudReceiptDao;
import org.example.repositories.dao.specificdao.ReceiptSpecificDao;

@RequiredArgsConstructor
public class ReceiptRepository implements CrudRepository<Receipt, Long> {

  private final CrudReceiptDao crudReceiptDao;
  private final ReceiptSpecificDao receiptSpecificDao;

  public List<Receipt> getReceiptsOfUserByUserId(Long userId) {
    if(userId == null){
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
    return crudReceiptDao.findAll();
  }

  @Override
  public Receipt findById(Long key) {
    return crudReceiptDao.findById(key);
  }
}
