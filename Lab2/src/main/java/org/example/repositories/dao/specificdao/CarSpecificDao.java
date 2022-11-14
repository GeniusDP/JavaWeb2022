package org.example.repositories.dao.specificdao;

import java.util.List;
import org.example.entities.car.Car;
import org.example.entities.car.QualityClass;

public interface CarSpecificDao {

  List<Car> findAllByMarkName(String markName);

  List<Car> findAllByQualityClass(QualityClass qualityClass);

  List<Car> findAllByPriceOrdered();

  List<Car> findAllByNameOrdered();

  Car findByName(String name);
}
