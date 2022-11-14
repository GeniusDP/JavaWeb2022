package org.example;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.example.controllers.main.Controller;
import org.example.repositories.CarRepository;
import org.example.repositories.MarkRepository;
import org.example.repositories.ReceiptRepository;
import org.example.repositories.UserRepository;
import org.example.repositories.dao.cruddao.CrudCarDao;
import org.example.repositories.dao.cruddao.CrudMarkDao;
import org.example.repositories.dao.cruddao.CrudReceiptDao;
import org.example.repositories.dao.cruddao.CrudUserDao;
import org.example.repositories.dao.specificdao.*;
import org.example.repositories.dbutils.ConnectionPool;
import org.example.views.main.MainView;

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

  public static void main(String[] args) throws IOException {
    Properties properties = new Properties();
    properties.load(new FileReader("src/main/resources/application.properties"));

    String url = properties.getProperty("database.url");
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
    ReceiptSpecificDao receiptSpecificDao;
    CrudReceiptDao crudReceiptDao;
    ReceiptRepository receiptRepository;
    MainView mainView;

    crudCarDao = new CrudCarDao(connectionPool);
    crudMarkDao = new CrudMarkDao(connectionPool);
    crudReceiptDao = new CrudReceiptDao(connectionPool);

    userDao = new CrudUserDao(connectionPool);
    markSpecificDao = new MarkSpecificDaoImpl(connectionPool);
    carSpecificDao = new CarSpecificDaoImpl(connectionPool);
    userSpecificDao = new UserSpecificDaoImpl(connectionPool);
    mainView = new MainView();

    markRepository = new MarkRepository(crudMarkDao, markSpecificDao);
    carRepository = new CarRepository(crudCarDao, markRepository, carSpecificDao);
    userRepository = new UserRepository(userDao, userSpecificDao);
    receiptSpecificDao = new ReceiptSpecificDao(connectionPool, userRepository, carRepository);
    receiptRepository = new ReceiptRepository(crudReceiptDao, receiptSpecificDao, carRepository, userRepository);
    Controller controller = new Controller(carRepository, markRepository, userRepository, receiptRepository, mainView);
    controller.start();
  }
}