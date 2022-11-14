package org.example.repositories.dao.specificdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.entities.user.User;
import org.example.exceptions.DatabaseException;
import org.example.repositories.dao.extractors.Extractor;
import org.example.repositories.dao.extractors.UserExtractor;
import org.example.repositories.dbutils.ConnectionPool;

public class UserSpecificDaoImpl implements UserSpecificDao {

  private final ConnectionPool connectionPool;
  private final Extractor<User> userExtractor;

  public UserSpecificDaoImpl(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
    this.userExtractor = new UserExtractor();
  }

  @Override
  public User findByUsername(String username) {
    if (username == null) {
      throw new IllegalArgumentException("username must not be null");
    }
    Connection connection = connectionPool.getConnection();

    String sql = "select * from lab_java.car_users where username = ?;";

    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return userExtractor.extract(resultSet);
      }
    } catch (SQLException e) {
      throw new DatabaseException(e);
    } finally {
      connectionPool.putBack(connection);
    }
    return null;
  }

  @Override
  public void setIsBanned(Long id, boolean isBanned) {
    if (id == null) {
      throw new IllegalArgumentException("id must not be null");
    }
    Connection connection = connectionPool.getConnection();

    String sql = "update lab_java.car_users set is_banned = ? where id = ?;";

    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setBoolean(1, isBanned);
      statement.setLong(2, id);
      statement.executeUpdate();
      statement.close();
    } catch (SQLException e) {
      throw new DatabaseException(e);
    } finally {
      connectionPool.putBack(connection);
    }
  }

}
