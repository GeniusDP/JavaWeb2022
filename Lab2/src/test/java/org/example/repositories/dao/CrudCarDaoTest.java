package org.example.repositories.dao;


import java.util.List;

import org.example.entities.*;
import org.example.repositories.CarRepository;
import org.example.repositories.CrudRepository;
import org.example.repositories.MarkRepository;
import org.example.repositories.dao.cruddao.CrudCarDao;
import org.example.repositories.dao.cruddao.CrudMarkDao;
import org.example.repositories.dao.cruddao.CrudUserDao;
import org.example.repositories.dao.specificdao.CarSpecificDao;
import org.example.repositories.dao.specificdao.CarSpecificDaoImpl;
import org.example.repositories.dao.specificdao.MarkSpecificDao;
import org.example.repositories.dao.specificdao.MarkSpecificDaoImpl;
import org.example.repositories.dbutils.ConnectionPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrudCarDaoTest {

  private CrudCarDao crudCarDao;
  private CrudMarkDao crudMarkDao;
  private MarkRepository markRepository;
  private CarSpecificDao carSpecificDao;
  private CrudUserDao userDao;

  @BeforeEach
  public void init(){
    String url = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=zaranik";
    ConnectionPool connectionPool = new ConnectionPool(url, "org.postgresql.Driver");
    crudCarDao = new CrudCarDao(connectionPool);
    crudMarkDao = new CrudMarkDao(connectionPool);
    userDao = new CrudUserDao(connectionPool);
    MarkSpecificDao markSpecificDao = new MarkSpecificDaoImpl(connectionPool);
    carSpecificDao = new CarSpecificDaoImpl(connectionPool);
    markRepository = new MarkRepository(crudMarkDao, markSpecificDao);
  }

  @Test
  void insert() {
    Car car = Car.builder()
        .name("Niva")
        .mark(new Mark(1L, "Lada"))
        .qualityClass(QualityClass.BASIC)
        .basePrice(2000)
        .build();
    Car insert = crudCarDao.insert(car);
    System.out.println(insert);
  }

  @Test
  void findAll() {
    List<Car> all = crudCarDao.findAll();
    all.forEach(System.out::println);
  }

  @Test
  void delete() {
    int deletedRows = crudCarDao.delete(10L);
    System.out.println(deletedRows);
    List<Car> all = crudCarDao.findAll();
    all.forEach(System.out::println);
  }

  @Test
  void findById() {
    Car byId = crudCarDao.findById(12L);
    System.out.println(byId);
    byId = crudCarDao.findById(100L);
    System.out.println(byId);
  }

  @Test
  void repositoryFindAllCars(){
    CrudRepository<Car, Long> carRepository = new CarRepository(crudCarDao, markRepository, carSpecificDao);
    List<Car> all = carRepository.findAll();
    all.forEach(System.out::println);
  }

  @Test
  void repositoryFindById() {
    CarRepository carRepository = new CarRepository(crudCarDao, markRepository, carSpecificDao);
    Car byId = carRepository.findById(12L);
    System.out.println(byId);
    byId = carRepository.findById(100L);
    System.out.println(byId);
  }

  @Test
  void repositoryFindAllCarsByMarkName(){
    CarRepository carRepository = new CarRepository(crudCarDao, markRepository, carSpecificDao);
    List<Car> all = carRepository.findAllByMarkName("Lexus");
    all.forEach(System.out::println);
  }

  @Test
  void repositoryFindAllCarsByQualityClass(){
    CarRepository carRepository = new CarRepository(crudCarDao, markRepository, carSpecificDao);
    List<Car> all = carRepository.findAllByQualityClass(QualityClass.BASIC);
    all.forEach(System.out::println);
  }

  @Test
  void findAllByPriceOrdered(){
    CarRepository carRepository = new CarRepository(crudCarDao, markRepository, carSpecificDao);
    List<Car> all = carRepository.findAllByPriceOrdered();
    all.forEach(System.out::println);
  }

  @Test
  void findAllByNameOrdered(){
    CarRepository carRepository = new CarRepository(crudCarDao, markRepository, carSpecificDao);
    List<Car> all = carRepository.findAllByNameOrdered();
    all.forEach(System.out::println);
  }

  @Test
  void insertUser(){
    User user = User.builder()
            .firstName("Bogdan")
            .lastName("Zaranik")
            .username("bz2002")
            .password("1234")
            .role(Role.CLIENT)
            .build();
    user = userDao.insert(user);
    System.out.println(user);
  }

  @Test
  void findAllUsers(){
    List<User> all = userDao.findAll();
    all.forEach(System.out::println);
  }

}