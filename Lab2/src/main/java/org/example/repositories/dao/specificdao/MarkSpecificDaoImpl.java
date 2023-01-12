package org.example.repositories.dao.specificdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.log4j.Log4j;
import org.example.entities.car.Mark;
import org.example.exceptions.DatabaseException;
import org.example.repositories.dao.extractors.Extractor;
import org.example.repositories.dao.extractors.MarkExtractor;
import org.example.repositories.dbutils.ConnectionPool;

@Log4j
public class MarkSpecificDaoImpl implements MarkSpecificDao{

  private final ConnectionPool connectionPool;
  private final Extractor<Mark> markExtractor;

  public MarkSpecificDaoImpl(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
    this.markExtractor = new MarkExtractor();
  }


  @Override
  public Mark findByName(String name) {
    if(name == null){
      throw new IllegalArgumentException("name must not be null");
    }
    Connection connection = connectionPool.getConnection();

    String sql = "select * from lab_java.marks where name = ?;";

    Mark result = null;
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, name);
      ResultSet resultSet = statement.executeQuery();
      if(resultSet.next()) {
        result = markExtractor.extract(resultSet);
      }
    } catch (SQLException e) {
      log.error(e);
      throw new DatabaseException(e);
    } finally {
      connectionPool.putBack(connection);
    }
    return result;
  }

}