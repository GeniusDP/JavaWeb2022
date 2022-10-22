package org.example.repositories;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.entities.Car;
import org.example.entities.Mark;
import org.example.repositories.dao.cruddao.CrudDao;
import org.example.repositories.dao.specificdao.CarSpecificDao;

@RequiredArgsConstructor
public class CarRepository implements CrudRepository<Car, Long> {

  private final CrudDao<Car, Long> crudCarDao;
  private final MarkRepository markRepository;
  private final CarSpecificDao carSpecificDao;

  @Override
  public Car insert(Car value) {
    return crudCarDao.insert(value);
  }

  @Override
  public int delete(Long key) {
    return crudCarDao.delete(key);
  }

  @Override
  public Car update(Long key, Car newValue) {
    return crudCarDao.update(key, newValue);
  }

  @Override
  public List<Car> findAll() {
    List<Car> allCars = crudCarDao.findAll();

    allCars = allCars.stream().map(car -> {
      Mark mark = car.getMark();
      if (mark == null || mark.getId() == null) {
        return car;
      }
      Long markId = mark.getId();
      mark = markRepository.findById(markId);
      car.setMark(mark);
      return car;
    }).collect(Collectors.toList());

    return allCars;
  }

  @Override
  public Car findById(Long key) {
    Car car = crudCarDao.findById(key);
    Mark mark = car.getMark();
    if (mark == null || mark.getId() == null) {
      return car;
    }
    Long markId = mark.getId();
    mark = markRepository.findById(markId);
    car.setMark(mark);
    return car;
  }

  public List<Car> findAllByMarkName(String markName) {
    return carSpecificDao.findAllByMarkName(markName);
  }
}
