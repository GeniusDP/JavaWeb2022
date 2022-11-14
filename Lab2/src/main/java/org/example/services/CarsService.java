package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entities.car.Car;
import org.example.entities.car.QualityClass;
import org.example.repositories.CarRepository;

import java.util.List;

@RequiredArgsConstructor
public class CarsService {

  private final CarRepository carRepository;

  public List<Car> getCarsByMark(String markName) {
    return carRepository.findAllByMarkName(markName);
  }

  public List<Car> getCarsByQualityClass(String qualityClass) {
    return carRepository.findAllByQualityClass(QualityClass.getQualityClass(qualityClass));
  }

  public List<Car> getCarsSortedByPrice() {
    return carRepository.findAllByPriceOrdered();
  }

  public List<Car> getCarsSortedByName() {
    return carRepository.findAllByNameOrdered();
  }

  public boolean existsById(long carId) {
    return carRepository.findById(carId) != null;
  }

  public Car getCarById(long id) {
    return carRepository.findById(id);
  }
}
