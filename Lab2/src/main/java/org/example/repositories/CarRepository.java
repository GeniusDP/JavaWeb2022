package org.example.repositories;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.entities.Car;
import org.example.entities.Mark;
import org.example.repositories.dao.CarDao;
import org.example.repositories.dao.MarkDao;

@RequiredArgsConstructor
public class CarRepository implements CrudRepository<Car, Long> {

  private final CarDao carDao;
  private final MarkDao markDao;

  @Override
  public Car insert(Car value) {
    return carDao.insert(value);
  }

  @Override
  public int delete(Long key) {
    return carDao.delete(key);
  }

  @Override
  public Car update(Long key, Car newValue) {
    return carDao.update(key, newValue);
  }

  @Override
  public List<Car> findAll() {
    List<Car> allCars = carDao.findAll();

    allCars = allCars.stream().map(car -> {
      Mark mark = car.getMark();
      if (mark == null || mark.getId() == null) {
        return car;
      }
      Long markId = mark.getId();
      mark = markDao.findById(markId);
      car.setMark(mark);
      return car;
    }).collect(Collectors.toList());

    return allCars;
  }

  @Override
  public Car findById(Long key) {
    Car car = carDao.findById(key);
    Mark mark = car.getMark();
    if (mark == null || mark.getId() == null) {
      return car;
    }
    Long markId = mark.getId();
    mark = markDao.findById(markId);
    car.setMark(mark);
    return car;
  }
}
