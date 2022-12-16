package org.example.repositories.dao.cruddao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.example.entities.car.Car;
import org.example.entities.car.Mark;
import org.example.entities.car.QualityClass;
import org.example.repositories.dao.extractors.CarExtractor;
import org.example.repositories.dao.extractors.Extractor;
import org.example.repositories.dbutils.ConnectionPool;

public class CrudCarDao extends AbstractCrudDao<Car, Long> {

  private final Extractor<Car> carExtractor;

  public CrudCarDao(ConnectionPool connectionPool) {
    super(connectionPool);
    carExtractor = new CarExtractor();
  }

  @Override
  protected Car insertInternal(Car car, Connection connection) throws SQLException {
    String sql = "insert into lab_java.cars(mark_id, quality_class, name, base_price) values(?, ?, ?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

    Mark mark = car.getMark();
    QualityClass qualityClass = car.getQualityClass();

    if (mark == null || mark.getId() == null) {
      statement.setNull(1, Types.BIGINT);
    } else {
      statement.setLong(1, mark.getId());
    }

    if (qualityClass == null) {
      statement.setNull(2, Types.VARCHAR);
    } else {
      statement.setString(2, qualityClass.name());
    }

    if (car.getName() == null) {
      statement.setNull(3, Types.VARCHAR);
    } else {
      statement.setString(3, car.getName());
    }

    if (car.getBasePrice() == null) {
      statement.setNull(4, Types.BIGINT);
    } else {
      statement.setInt(4, car.getBasePrice());
    }

    statement.executeUpdate();

    ResultSet generatedKeys = statement.getGeneratedKeys();
    if (generatedKeys.next()) {
      car.setId(generatedKeys.getLong(1));
    } else {
      throw new SQLException("Creating car failed, no ID obtained.");
    }
    generatedKeys.close();

    statement.close();
    return car;
  }

  @Override
  protected int deleteInternal(Long key, Connection connection) throws SQLException {
    String sql = "delete from lab_java.cars where id = ?;";
    int rowsUpdated = 0;
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, key);
      rowsUpdated = statement.executeUpdate();
    }
    return rowsUpdated;
  }

  /*
   * newCar.key is ignored
   * */
  @Override
  protected Car updateInternal(Long key, Car newCar, Connection connection) throws SQLException {
    updateMark(connection, key, newCar.getMark());
    updateQualityClass(connection, key, newCar.getQualityClass());
    updateName(connection, key, newCar.getName());
    updateBasePrice(connection, key, newCar.getBasePrice());
    return newCar;
  }

  private void updateBasePrice(Connection connection, Long key, Integer basePrice)
      throws SQLException {
    String sqlUpdMark = "update lab_java.cars set base_price = ? where id = ?";
    PreparedStatement statement = connection.prepareStatement(sqlUpdMark);
    if (basePrice == null) {
      statement.setNull(1, Types.INTEGER);
    } else {
      statement.setInt(1, basePrice);
    }

    statement.setLong(2, key);
    statement.executeUpdate();

    statement.close();
  }

  private void updateName(Connection connection, Long key, String name) throws SQLException {
    String sqlUpdMark = "update lab_java.cars set name = ? where id = ?;";
    PreparedStatement statement = connection.prepareStatement(sqlUpdMark);
    if (name == null) {
      statement.setNull(1, Types.VARCHAR);
    } else {
      statement.setString(1, name);
    }

    statement.setLong(2, key);
    statement.executeUpdate();

    statement.close();
  }

  private void updateQualityClass(Connection connection, Long key, QualityClass qualityClass)
      throws SQLException {
    String sqlUpdMark = "update lab_java.cars set quality_class = ? where id = ?;";
    PreparedStatement statement = connection.prepareStatement(sqlUpdMark);
    if (qualityClass == null) {
      statement.setNull(1, Types.VARCHAR);
    } else {
      statement.setString(1, qualityClass.name());
    }

    statement.setLong(2, key);
    statement.executeUpdate();

    statement.close();
  }

  private void updateMark(Connection connection, Long key, Mark mark) throws SQLException {
    String sqlUpdMark = "update lab_java.cars set mark_id = ? where id = ?;";
    PreparedStatement statement = connection.prepareStatement(sqlUpdMark);
    if (mark == null || mark.getId() == null) {
      statement.setNull(1, Types.BIGINT);
    } else {
      statement.setLong(1, mark.getId());
    }

    statement.setLong(2, key);
    statement.executeUpdate();

    statement.close();
  }


  @Override
  protected List<Car> findAllInternal(Connection connection) throws SQLException {
    String sql = "select * from lab_java.cars;";
    List<Car> cars = new ArrayList<>();
    try (PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        cars.add(carExtractor.extract(resultSet));
      }
    }
    return cars;
  }

  @Override
  protected Car findByIdInternal(Long key, Connection connection) throws SQLException {
    String sql = "select * from lab_java.cars where id = ?;";

    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, key);

    Car car = null;
    ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
      car = carExtractor.extract(resultSet);
    }
    resultSet.close();

    statement.close();
    return car;
  }


}
