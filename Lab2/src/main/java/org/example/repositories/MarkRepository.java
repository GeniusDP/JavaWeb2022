package org.example.repositories;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.entities.Mark;
import org.example.repositories.dao.CrudDao;

@RequiredArgsConstructor
public class MarkRepository implements CrudRepository<Mark, Long> {

  private final CrudDao<Mark, Long> markDao;

  @Override
  public Mark insert(Mark value) {
    return markDao.insert(value);
  }

  @Override
  public int delete(Long key) {
    return markDao.delete(key);
  }

  @Override
  public Mark update(Long key, Mark newValue) {
    return markDao.update(key, newValue);
  }

  @Override
  public List<Mark> findAll() {
    return markDao.findAll();
  }

  @Override
  public Mark findById(Long key) {
    return markDao.findById(key);
  }
}
