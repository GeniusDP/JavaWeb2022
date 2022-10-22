package org.example.repositories.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.example.repositories.dbutils.ConnectionPool;

public abstract class AbstractCrudDao<T, K> implements CrudDao<T, K> {

  private final ConnectionPool connectionPool;

  public AbstractCrudDao(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

  @Override
  public T insert(T value) throws SQLException {
    if (value == null) {
      throw new IllegalArgumentException("mark must not be null");
    }
    Connection connection = connectionPool.getConnection();

    T newValue = insertInternal(value, connection);

    connectionPool.putBack(connection);
    return newValue;
  }

  @Override
  public int delete(K key) throws SQLException {
    if (key == null) {
      throw new IllegalArgumentException("key must not be null");
    }
    Connection connection = connectionPool.getConnection();

    int count = deleteInternal(key, connection);

    connectionPool.putBack(connection);
    return count;
  }

  @Override
  public T update(K key, T newValue) throws SQLException {
    if (newValue == null || key == null) {
      throw new IllegalArgumentException("car must not be null");
    }
    Connection connection = connectionPool.getConnection();

    T t = updateInternal(key, newValue, connection);

    connectionPool.putBack(connection);
    return t;
  }

  @Override
  public List<T> findAll() throws SQLException {
    Connection connection = connectionPool.getConnection();

    List<T> all = findAllInternal(connection);

    connectionPool.putBack(connection);
    return all;
  }

  @Override
  public T findById(K key) throws SQLException {
    if (key == null) {
      throw new IllegalArgumentException("key must not be null");
    }
    Connection connection = connectionPool.getConnection();

    T byIdInternal = findByIdInternal(key, connection);

    connectionPool.putBack(connection);
    return byIdInternal;
  }

  public abstract T insertInternal(T value, Connection connection) throws SQLException;

  public abstract int deleteInternal(K key, Connection connection) throws SQLException;

  public abstract T updateInternal(K key, T newValue, Connection connection) throws SQLException;

  public abstract List<T> findAllInternal(Connection connection) throws SQLException;

  public abstract T findByIdInternal(K key, Connection connection) throws SQLException;

}
