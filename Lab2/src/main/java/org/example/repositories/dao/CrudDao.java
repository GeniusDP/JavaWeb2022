package org.example.repositories.dao;

import java.sql.SQLException;
import java.util.List;
import org.example.entities.Car;

public interface CrudDao<T, K> {

  T insert(T value) throws SQLException;

  int delete(K key) throws SQLException;

  T update(K key, T newValue) throws SQLException;

  List<T> findAll() throws SQLException;

  T findById(K key) throws SQLException;

}
