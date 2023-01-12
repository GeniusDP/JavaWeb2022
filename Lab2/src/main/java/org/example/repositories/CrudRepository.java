package org.example.repositories;

import java.util.List;

public interface CrudRepository<T, K> {

  T insert(T value);

  int delete(K key);

  T update(K key, T newValue);

  List<T> findAll();

  T findById(K key);

}
