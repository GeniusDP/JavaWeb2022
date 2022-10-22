package org.example.repositories.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.example.entities.Mark;
import org.example.repositories.dbutils.ConnectionPool;

public class MarkDao extends AbstractCrudDao<Mark, Long> {

  public MarkDao(ConnectionPool connectionPool) {
    super(connectionPool);
  }

  @Override
  public Mark insertInternal(Mark mark, Connection connection) throws SQLException {
    String sql = "insert into marks(name) values (?);";

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

    return mark;
  }

  @Override
  public int deleteInternal(Long key, Connection connection) throws SQLException {
    String sql = "delete from marks where id = ?;";
    int rowsUpdated = 0;
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, key);
      rowsUpdated = statement.executeUpdate();
    }
    return rowsUpdated;
  }

  @Override
  public Mark updateInternal(Long key, Mark newValue, Connection connection) {
    throw new IllegalArgumentException("not implemented yet");
  }

  @Override
  public List<Mark> findAllInternal(Connection connection) throws SQLException {
    String sql = "select * from marks;";
    List<Mark> marks = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        marks.add(extractMark(resultSet));
      }
    }

    return marks;
  }

  @Override
  public Mark findByIdInternal(Long key, Connection connection) throws SQLException {
    String sql = "select * from marks where id = ?;";

    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, key);

    Mark mark = null;
    ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
      mark = extractMark(resultSet);
    }
    resultSet.close();

    statement.close();
    return mark;
  }

  private Mark extractMark(ResultSet resultSet) throws SQLException {
    long id = resultSet.getLong("id");
    String name = resultSet.getString("name");
    return new Mark(id, name);
  }


}
