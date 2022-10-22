package org.example.repositories.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.example.entities.Car;
import org.example.entities.Mark;
import org.example.entities.QualityClass;
import org.example.repositories.dbutils.ConnectionPool;

public class CarDao implements CrudDao<Car, Long> {

  private final ConnectionPool connectionPool;

  public CarDao(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

  @Override
  public Car insert(Car car) throws SQLException {
    if (car == null) {
      throw new IllegalArgumentException("car must not be null");
    }
    String sql = """
          insert into cars(mark_id, quality_class, name, base_price)
          values(?, ?, ?, ?)
        """;
    Connection connection = connectionPool.getConnection();

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

    statement.setString(3, car.getName());
    statement.setInt(4, car.getBasePrice());

    statement.executeUpdate();

    ResultSet generatedKeys = statement.getGeneratedKeys();
    if (generatedKeys.next()) {
      car.setId(generatedKeys.getLong(1));
    } else {
      throw new SQLException("Creating car failed, no ID obtained.");
    }
    generatedKeys.close();

    statement.close();

    connectionPool.putBack(connection);
    return car;
  }

  @Override
  public int delete(Long key) throws SQLException {
    if (key == null) {
      throw new IllegalArgumentException("key must not be null");
    }
    String sql = "delete from cars where id = ?;";
    Connection connection = connectionPool.getConnection();

    int rowsUpdated = 0;
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, key);
      rowsUpdated = statement.executeUpdate();
    }

    connectionPool.putBack(connection);
    return rowsUpdated;
  }

  @Override
  public int update(Long key, Car newValue) throws SQLException {
    if (newValue == null || key == null) {
      throw new IllegalArgumentException("car must not be null");
    }
    String sql = """
          insert into cars(mark_id, quality_class, name, base_price)
          values(?, ?, ?, ?)
        """;
    Connection connection = connectionPool.getConnection();

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

    statement.setString(3, car.getName());
    statement.setInt(4, car.getBasePrice());

    statement.executeUpdate();

    ResultSet generatedKeys = statement.getGeneratedKeys();
    if (generatedKeys.next()) {
      car.setId(generatedKeys.getLong(1));
    } else {
      throw new SQLException("Creating car failed, no ID obtained.");
    }
    generatedKeys.close();

    statement.close();

    connectionPool.putBack(connection);
    return car;

  }

  @Override
  public List<Car> findAll() throws SQLException {
    String sql = "select * from cars;";
    List<Car> cars = new ArrayList<>();
    Connection connection = connectionPool.getConnection();

    try (PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        cars.add(extractCar(resultSet));
      }
    }

    connectionPool.putBack(connection);
    return cars;
  }

  @Override
  public Car findById(Long key) throws SQLException {
    if (key == null) {
      throw new IllegalArgumentException("key must not be null");
    }
    String sql = "select * from cars where id = ?;";
    Connection connection = connectionPool.getConnection();

    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, key);

    Car car = null;
    ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
      car = extractCar(resultSet);
    }
    resultSet.close();

    statement.close();
    connectionPool.putBack(connection);
    return car;
  }

  private Car extractCar(ResultSet resultSet) throws SQLException {
    long id = resultSet.getLong("id");
    long mark_id = resultSet.getLong("mark_id");
    QualityClass qualityClass = QualityClass.getQualityClass(resultSet.getString("quality_class"));
    String name = resultSet.getString("name");
    int basePrice = resultSet.getInt("base_price");

    return Car.builder()
        .id(id)
        .mark(new Mark(mark_id, null))
        .qualityClass(qualityClass)
        .name(name)
        .basePrice(basePrice)
        .build();
  }

}
