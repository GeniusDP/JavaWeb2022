package org.example.repositories.dao.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface Extractor<T> {

  T extract(ResultSet rs) throws SQLException;

}
