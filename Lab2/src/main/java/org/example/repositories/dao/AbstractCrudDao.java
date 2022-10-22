package org.example.repositories.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.example.exceptions.DatabaseException;
import org.example.repositories.dbutils.ConnectionPool;

public abstract class AbstractCrudDao<T, K> implements CrudDao<T, K> {

  private final ConnectionPool connectionPool;

  public AbstractCrudDao(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

  @Override
  public T insert(T value) {
    if (value == null) {
      throw new IllegalArgumentException("mark must not be null");
    }
    Connection connection = connectionPool.getConnection();

    T newValue = null;
    try {
      newValue = insertInternal(value, connection);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }

    connectionPool.putBack(connection);
    return newValue;
  }

  @Override
  public int delete(K key) {
    if (key == null) {
      throw new IllegalArgumentException("key must not be null");
    }
    Connection connection = connectionPool.getConnection();

    int count = 0;
    try {
      count = deleteInternal(key, connection);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }

    connectionPool.putBack(connection);
    return count;
  }

  @Override
  public T update(K key, T newValue) {
    if (newValue == null || key == null) {
      throw new IllegalArgumentException("car must not be null");
    }
    Connection connection = connectionPool.getConnection();

    T t = null;
    try {
      t = updateInternal(key, newValue, connection);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }

    connectionPool.putBack(connection);
    return t;
  }

  @Override
  public List<T> findAll() {
    Connection connection = connectionPool.getConnection();

    List<T> all = null;
    try {
      all = findAllInternal(connection);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }

    connectionPool.putBack(connection);
    return all;
  }

  @Override
  public T findById(K key) {
    if (key == null) {
      throw new IllegalArgumentException("key must not be null");
    }
    Connection connection = connectionPool.getConnection();

    T byIdInternal = null;
    try {
      byIdInternal = findByIdInternal(key, connection);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }

    connectionPool.putBack(connection);
    return byIdInternal;
  }

  protected abstract T insertInternal(T value, Connection connection) throws SQLException;

  protected abstract int deleteInternal(K key, Connection connection) throws SQLException;

  protected abstract T updateInternal(K key, T newValue, Connection connection) throws SQLException;

  protected abstract List<T> findAllInternal(Connection connection) throws SQLException;

  protected abstract T findByIdInternal(K key, Connection connection) throws SQLException;

}
