package org.example.repositories.dao;


import java.sql.SQLException;
import java.util.List;
import org.example.entities.Car;
import org.example.entities.Mark;
import org.example.entities.QualityClass;
import org.example.repositories.dbutils.ConnectionPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarDaoTest {

  private CrudDao<Car, Long> dao;
  @BeforeEach
  public void init(){
    String url = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=zaranik";
    ConnectionPool connectionPool = new ConnectionPool(url, "org.postgresql.Driver");
    dao = new CarDao(connectionPool);
  }

  @Test
  void insert() throws SQLException {
    Car car = Car.builder()
        .name("Niva")
        .mark(new Mark(1L, "Lada"))
        .qualityClass(QualityClass.BASIC)
        .basePrice(2000)
        .build();
    Car insert = dao.insert(car);
    System.out.println(insert);
  }

  @Test
  void findAll() throws SQLException {
    List<Car> all = dao.findAll();
    all.forEach(System.out::println);
  }

  @Test
  void delete() throws SQLException {
    int deletedRows = dao.delete(10L);
    System.out.println(deletedRows);
    List<Car> all = dao.findAll();
    all.forEach(System.out::println);
  }

  @Test
  void update() {

  }

  @Test
  void findById() throws SQLException {
    Car byId = dao.findById(12L);
    System.out.println(byId);
    byId = dao.findById(100L);
    System.out.println(byId);
  }
}