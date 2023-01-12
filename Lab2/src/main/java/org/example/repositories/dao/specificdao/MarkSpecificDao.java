package org.example.repositories.dao.specificdao;

import org.example.entities.car.Mark;

public interface MarkSpecificDao {

  Mark findByName(String name);
}
