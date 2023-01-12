package org.example.repositories.dao.cruddao;

import java.util.List;

public interface CrudDao<T, K> {

  T insert(T value);

  int delete(K key);

  T update(K key, T newValue);

  List<T> findAll();

  T findById(K key);

}
