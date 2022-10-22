package org.example.repositories.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.example.repositories.dbutils.ConnectionPool;

public abstract class AbstractCrudDao<T, K> implements CrudDao<T, K> {

  @Override
  public T insert(T value) throws SQLException {
    return null;
  }

  @Override
  public int delete(K key) throws SQLException {
    return 0;
  }

  @Override
  public T update(K key, T newValue) throws SQLException {
    return null;
  }

  @Override
  public List<T> findAll() throws SQLException {
    return null;
  }

  @Override
  public T findById(K key) throws SQLException {
    return null;
  }

  public abstract T insertInternal(T value, Connection connection);
}
