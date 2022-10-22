package org.example.repositories.dao.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.entities.Mark;

public class MarkExtractor implements Extractor<Mark> {

  @Override
  public Mark extract(ResultSet resultSet) throws SQLException {
    Long id = resultSet.getObject("id", Long.class);
    String name = resultSet.getString("name");
    return new Mark(id, name);
  }
}
