package org.example.repositories.dao.specificdao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.example.entities.Car;
import org.example.entities.Mark;
import org.example.entities.QualityClass;
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
          select cars.id, mark_id, quality_class, cars.name, base_price from lab_java.cars
          inner join lab_java.marks as m on m.id = lab_java.cars.mark_id
          where m.name = ?;
        """;
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, markName);
      return extractList(statement);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    } finally {
      connectionPool.putBack(connection);
    }
  }

  @Override
  public List<Car> findAllByQualityClass(QualityClass qualityClass) {
    if (qualityClass == null) {
      throw new IllegalArgumentException("qualityClass must not be null");
    }
    Connection connection = connectionPool.getConnection();

    String sql = """
        select * from lab_java.cars where quality_class = ?
        """;
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, qualityClass.name());
      return extractList(statement);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    } finally {
      connectionPool.putBack(connection);
    }
  }

  @Override
  public List<Car> findAllByPriceOrdered() {
    Connection connection = connectionPool.getConnection();

    String sql = """
        select * from lab_java.cars order by base_price;
        """;
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      return extractList(statement);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    } finally {
      connectionPool.putBack(connection);
    }
  }

  @Override
  public List<Car> findAllByNameOrdered() {
    Connection connection = connectionPool.getConnection();

    String sql = """
        select * from lab_java.cars order by name;
        """;
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      return extractList(statement);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    } finally {
      connectionPool.putBack(connection);
    }
  }


  private List<Car> extractList(PreparedStatement statement) throws SQLException {
    List<Car> cars = new ArrayList<>();
    ResultSet resultSet = statement.executeQuery();
    while (resultSet.next()) {
      cars.add(carExtractor.extract(resultSet));
    }
    return cars.stream().map(car -> {
      Mark mark = car.getMark();
      if (mark == null || mark.getId() == null) {
        return car;
      }
      Long markId = mark.getId();
      mark = crudMarkDao.findById(markId);
      car.setMark(mark);
      return car;
    }).collect(Collectors.toList());
  }
}
