package org.example.repositories.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T, K> {

  T insert(T value) throws SQLException;

  int delete(K key) throws SQLException;

  int update(K key, T newValue) throws SQLException;

  List<T> findAll() throws SQLException;

  T findById(K key) throws SQLException;

}
