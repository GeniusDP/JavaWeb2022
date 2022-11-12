package org.example;

import org.example.controllers.main.Controller;
import org.example.repositories.CarRepository;
import org.example.repositories.MarkRepository;
import org.example.repositories.UserRepository;
import org.example.repositories.dao.cruddao.CrudCarDao;
import org.example.repositories.dao.cruddao.CrudMarkDao;
import org.example.repositories.dao.cruddao.CrudUserDao;
import org.example.repositories.dao.specificdao.*;
import org.example.repositories.dbutils.ConnectionPool;
import org.example.views.main.MainView;

import java.sql.SQLException;
/*
В системі існує перелік Автомобілів, для якого необхідно реалізувати:
- вибір по марці;
- вибір по класу якості;
- сортування за вартістю прокату;
- сортування за назвою.
Клієнт реєструється в системі, обирає автомобіль та робить замовлення на оренду.
Незареєстрований клієнт не може зробити замовлення. В даних замовлення клієнт вказує
паспортні дані, опцію 'з водієм'/'без водія', термін оренди. Система формує Рахунок, який
клієнт оплачує.
Менеджер розглядає замовлення і може відхилити його, вказавши причину. Також менеджер
реєструє повернення автомобіля, у випадку пошкодження автомобіля виставляє через систему
рахунок на ремонт.
Адміністратор системи має права:
- додавання, видалення автомобілів, редагування інформації про автомобілі;
- блокування/розблокування користувачів;
- реєстрація менеджерів в системі.
 */
public class Main {

  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=zaranik";
    ConnectionPool connectionPool = new ConnectionPool(url, "org.postgresql.Driver");

    CrudCarDao crudCarDao;
    CrudMarkDao crudMarkDao;
    CarSpecificDao carSpecificDao;
    CrudUserDao userDao;
    MarkSpecificDao markSpecificDao;
    MarkRepository markRepository;
    CarRepository carRepository;
    UserRepository userRepository;
    UserSpecificDao userSpecificDao;
    MainView mainView;

    crudCarDao = new CrudCarDao(connectionPool);
    crudMarkDao = new CrudMarkDao(connectionPool);
    userDao = new CrudUserDao(connectionPool);
    markSpecificDao = new MarkSpecificDaoImpl(connectionPool);
    carSpecificDao = new CarSpecificDaoImpl(connectionPool);
    userSpecificDao = new UserSpecificDaoImpl(connectionPool);
    mainView = new MainView();

    markRepository = new MarkRepository(crudMarkDao, markSpecificDao);
    carRepository = new CarRepository(crudCarDao, markRepository, carSpecificDao);
    userRepository = new UserRepository(userDao, userSpecificDao);
    Controller controller = new Controller(carRepository, markRepository, userRepository, mainView);
    controller.start();
  }
}