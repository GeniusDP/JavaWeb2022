package org.example.repositories.dao.specificdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.example.entities.Car;
import org.example.entities.Mark;
import org.example.exceptions.DatabaseException;
import org.example.repositories.dao.cruddao.CrudMarkDao;
import org.example.repositories.dao.extractors.CarExtractor;
import org.example.repositories.dao.extractors.Extractor;
import org.example.repositories.dbutils.ConnectionPool;

public class CarSpecificDaoImpl implements CarSpecificDao {

  private ConnectionPool connectionPool;
  private Extractor<Car> carExtractor;
  private CrudMarkDao crudMarkDao;

  public CarSpecificDaoImpl(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
    this.carExtractor = new CarExtractor();
    this.crudMarkDao = new CrudMarkDao(connectionPool);
  }

  @Override
  public List<Car> findAllByMarkName(String markName) {
    if (markName == null) {
      throw new IllegalArgumentException("name must not be null");
    }
    Connection connection = connectionPool.getConnection();

    String sql = """
          select cars.id, mark_id, quality_class, cars.name, base_price from cars
          inner join marks as m on m.id = cars.mark_id
          where m.name = ?;
        """;
    List<Car> cars = new ArrayList<>();
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, markName);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        cars.add(carExtractor.extract(resultSet));
      }
      cars = cars.stream().map(car -> {
        Mark mark = car.getMark();
        if (mark == null || mark.getId() == null) {
          return car;
        }
        Long markId = mark.getId();
        mark = crudMarkDao.findById(markId);
        car.setMark(mark);
        return car;
      }).collect(Collectors.toList());

    } catch (SQLException e) {
      throw new DatabaseException(e);
    }

    connectionPool.putBack(connection);
    return cars;
  }
}
