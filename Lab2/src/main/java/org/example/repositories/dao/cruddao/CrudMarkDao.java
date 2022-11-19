package org.example.repositories.dao.cruddao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.example.entities.car.Mark;
import org.example.repositories.dao.extractors.Extractor;
import org.example.repositories.dao.extractors.MarkExtractor;
import org.example.repositories.dbutils.ConnectionPool;

public class CrudMarkDao extends AbstractCrudDao<Mark, Long> {

  private final Extractor<Mark> markExtractor;

  public CrudMarkDao(ConnectionPool connectionPool) {
    super(connectionPool);
    markExtractor = new MarkExtractor();
  }

  @Override
  protected Mark insertInternal(Mark mark, Connection connection) throws SQLException {
    String sql = "insert into lab_java.marks(name) values (?);";

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
  protected int deleteInternal(Long key, Connection connection) throws SQLException {
    String sql = "delete from lab_java.marks where id = ?;";
    int rowsUpdated = 0;
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, key);
      rowsUpdated = statement.executeUpdate();
    }
    return rowsUpdated;
  }

  @Override
  protected Mark updateInternal(Long key, Mark newValue, Connection connection) {
    throw new IllegalArgumentException("not implemented yet");
  }

  @Override
  protected List<Mark> findAllInternal(Connection connection) throws SQLException {
    String sql = "select * from lab_java.marks;";
    List<Mark> marks = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        marks.add(markExtractor.extract(resultSet));
      }
    }

    return marks;
  }

  @Override
  protected Mark findByIdInternal(Long key, Connection connection) throws SQLException {
    String sql = "select * from lab_java.marks where id = ?;";

    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, key);

    Mark mark = null;
    ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
      mark = markExtractor.extract(resultSet);
    }
    resultSet.close();

    statement.close();
    return mark;
  }

}
