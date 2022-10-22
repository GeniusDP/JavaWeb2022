package org.example.repositories.dao.specificdao;

import java.util.List;
import org.example.entities.Car;

public interface CarSpecificDao {

  List<Car> findAllByMarkName(String markName);
}
