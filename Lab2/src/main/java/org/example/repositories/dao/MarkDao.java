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

public class MarkDao implements CrudDao<Mark, Long> {

  private final ConnectionPool connectionPool;

  public MarkDao(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

  @Override
  public Mark insert(Mark mark) throws SQLException {
    if (mark == null) {
      throw new IllegalArgumentException("mark must not be null");
    }
    String sql = "insert into marks(name) values (?);";
    Connection connection = connectionPool.getConnection();

    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

    if (mark.getName() == null) {
      statement.setNull(1, Types.VARCHAR);
    } else {
      statement.setString(1, mark.getName());
    }

    statement.executeUpdate();

    ResultSet generatedKeys = statement.getGeneratedKeys();
    if (generatedKeys.next()) {
      mark.setId(generatedKeys.getLong(1));
    } else {
      throw new SQLException("Creating mark failed, no ID obtained.");
    }
    generatedKeys.close();

    statement.close();

    connectionPool.putBack(connection);
    return mark;
  }

  @Override
  public int delete(Long key) throws SQLException {
    if (key == null) {
      throw new IllegalArgumentException("key must not be null");
    }
    String sql = "delete from marks where id = ?;";
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
  public Mark update(Long key, Mark newValue) throws SQLException {
    throw new IllegalArgumentException("not implemented yet");
  }

  @Override
  public List<Mark> findAll() throws SQLException {
    Connection connection = connectionPool.getConnection();
    String sql = "select * from marks;";
    List<Mark> marks = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        marks.add(extractMark(resultSet));
      }
    }

    connectionPool.putBack(connection);
    return marks;
  }

  @Override
  public Mark findById(Long key) throws SQLException {
    if (key == null) {
      throw new IllegalArgumentException("key must not be null");
    }
    String sql = "select * from marks where id = ?;";
    Connection connection = connectionPool.getConnection();

    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, key);

    Mark mark = null;
    ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
      mark = extractMark(resultSet);
    }
    resultSet.close();

    statement.close();
    connectionPool.putBack(connection);
    return mark;
  }

  private Mark extractMark(ResultSet resultSet) throws SQLException {
    long id = resultSet.getLong("id");
    String name = resultSet.getString("name");
    return new Mark(id, name);
  }


}
