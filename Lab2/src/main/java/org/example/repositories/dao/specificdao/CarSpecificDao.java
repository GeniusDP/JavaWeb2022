package org.example.repositories.dao.specificdao;

import java.util.List;
import org.example.entities.Car;
import org.example.entities.QualityClass;

public interface CarSpecificDao {

  List<Car> findAllByMarkName(String markName);

  List<Car> findAllByQualityClass(QualityClass qualityClass);

  List<Car> findAllByPriceOrdered();

  List<Car> findAllByNameOrdered();
}
