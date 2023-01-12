package org.example.repositories.dao.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.entities.car.Car;
import org.example.entities.car.Mark;
import org.example.entities.car.QualityClass;

public class CarExtractor implements Extractor<Car> {

  @Override
  public Car extract(ResultSet resultSet) throws SQLException {
    Long id = resultSet.getObject("id", Long.class);
    Long mark_id = resultSet.getObject("mark_id", Long.class);
    QualityClass qualityClass = QualityClass.getQualityClass(
        resultSet.getString("quality_class"));
    String name = resultSet.getString("name");
    Integer basePrice = resultSet.getObject("base_price", Integer.class);

    return Car.builder().id(id).mark(new Mark(mark_id, null)).qualityClass(qualityClass)
        .name(name)
        .basePrice(basePrice).build();
  }
}
