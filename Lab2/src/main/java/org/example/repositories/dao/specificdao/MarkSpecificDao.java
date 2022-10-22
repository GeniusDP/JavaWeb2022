package org.example.repositories.dao.specificdao;

import org.example.entities.Mark;

public interface MarkSpecificDao {

  Mark findByName(String name);
}
