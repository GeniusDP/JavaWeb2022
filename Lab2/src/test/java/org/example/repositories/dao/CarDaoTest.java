package org.example.repositories.dao;


import java.sql.SQLException;
import java.util.List;
import org.example.entities.Car;
import org.example.entities.Mark;
import org.example.entities.QualityClass;
import org.example.repositories.CarRepository;
import org.example.repositories.CrudRepository;
import org.example.repositories.dbutils.ConnectionPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarDaoTest {

  private CrudDao<Car, Long> carDao;
  private CrudDao<Mark, Long> markDao;

  @BeforeEach
  public void init(){
    String url = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=zaranik";
    ConnectionPool connectionPool = new ConnectionPool(url, "org.postgresql.Driver");
    carDao = new CarDao(connectionPool);
    markDao = new MarkDao(connectionPool);
  }

  @Test
  void insert() {
    Car car = Car.builder()
        .name("Niva")
        .mark(new Mark(1L, "Lada"))
        .qualityClass(QualityClass.BASIC)
        .basePrice(2000)
        .build();
    Car insert = carDao.insert(car);
    System.out.println(insert);
  }

  @Test
  void findAll() {
    List<Car> all = carDao.findAll();
    all.forEach(System.out::println);
  }

  @Test
  void delete() {
    int deletedRows = carDao.delete(10L);
    System.out.println(deletedRows);
    List<Car> all = carDao.findAll();
    all.forEach(System.out::println);
  }

  @Test
  void update() {

  }

  @Test
  void findById() {
    Car byId = carDao.findById(21L);
    System.out.println(byId);
    byId = carDao.findById(100L);
    System.out.println(byId);
  }

  @Test
  void repositoryFindAll(){
    CrudRepository<Car, Long> carRepository = new CarRepository((CarDao) carDao, (MarkDao) markDao);
    List<Car> all = carRepository.findAll();
    all.forEach(System.out::println);
  }
}