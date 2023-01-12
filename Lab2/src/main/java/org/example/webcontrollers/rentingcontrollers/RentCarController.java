package org.example.webcontrollers.rentingcontrollers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import org.example.entities.car.Car;
import org.example.entities.receipt.Receipt;
import org.example.entities.receipt_decorator.AbstractReceiptEntity;
import org.example.entities.receipt_decorator.BasicReceipt;
import org.example.entities.receipt_decorator.DaysRentAddition;
import org.example.entities.receipt_decorator.DriverAddition;
import org.example.entities.receipt_decorator.ReceiptEntity;
import org.example.entities.user.User;
import org.example.exceptions.DatabaseException;
import org.example.repositories.CarRepository;
import org.example.repositories.MarkRepository;
import org.example.repositories.ReceiptRepository;
import org.example.repositories.UserRepository;
import org.example.repositories.dao.cruddao.CrudCarDao;
import org.example.repositories.dao.cruddao.CrudMarkDao;
import org.example.repositories.dao.cruddao.CrudReceiptDao;
import org.example.repositories.dao.cruddao.CrudUserDao;
import org.example.repositories.dao.specificdao.CarSpecificDao;
import org.example.repositories.dao.specificdao.CarSpecificDaoImpl;
import org.example.repositories.dao.specificdao.MarkSpecificDaoImpl;
import org.example.repositories.dao.specificdao.ReceiptSpecificDao;
import org.example.repositories.dao.specificdao.UserSpecificDaoImpl;
import org.example.repositories.dbutils.ConnectionPool;
import org.example.services.CarsService;
import org.example.services.ReceiptService;
import org.example.services.UserService;

@Log4j
@WebServlet(name = "RentCarController", urlPatterns = "/menu/rent-car")
public class RentCarController extends HttpServlet {

  private CarsService carsService;
  private ReceiptService receiptService;
  private UserService userService;

  @Override
  public void init() {
    CrudMarkDao crudMarkDao = new CrudMarkDao(ConnectionPool.getInstance());
    MarkSpecificDaoImpl markSpecificDao = new MarkSpecificDaoImpl(ConnectionPool.getInstance());
    MarkRepository markRepository = new MarkRepository(crudMarkDao, markSpecificDao);
    CarSpecificDao carSpecificDao = new CarSpecificDaoImpl(ConnectionPool.getInstance());
    CrudCarDao crudCarDao = new CrudCarDao(ConnectionPool.getInstance());

    CarRepository carRepository = new CarRepository(crudCarDao, markRepository, carSpecificDao);
    carsService = new CarsService(carRepository);

    CrudUserDao userDao = new CrudUserDao(ConnectionPool.getInstance());
    UserSpecificDaoImpl userSpecificDao = new UserSpecificDaoImpl(ConnectionPool.getInstance());
    UserRepository userRepository = new UserRepository(userDao, userSpecificDao);
    CrudReceiptDao crudReceiptDao = new CrudReceiptDao(ConnectionPool.getInstance());
    ReceiptSpecificDao receiptSpecificDao = new ReceiptSpecificDao(ConnectionPool.getInstance(), userRepository, carRepository);
    ReceiptRepository receiptRepository = new ReceiptRepository(crudReceiptDao, receiptSpecificDao, carRepository, userRepository);
    receiptService = new ReceiptService(receiptRepository);
    userService = new UserService(userRepository);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/pages/car/rent-car.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      int carId = Integer.parseInt(request.getParameter("carId"));
      boolean driverNeeded = Boolean.parseBoolean(request.getParameter("driverNeeded"));
      int daysRent = Integer.parseInt(request.getParameter("daysRent"));
      System.out.println("carId = " + carId);
      System.out.println("driverNeeded = " + driverNeeded);
      System.out.println("daysRent = " + daysRent);
      String username = (String) request.getAttribute("username");

      if (carsService.existsById(carId)) {
        Car car = carsService.getCarById(carId);
        if (receiptService.carIsAvailable(carId)) {
          User user = userService.findByUsername(username);
          Receipt receipt = Receipt.builder().car(car).user(user).build();

          AbstractReceiptEntity receiptEntity = new BasicReceipt(car, user);

          receiptEntity = applyAdditions(receiptEntity, receipt, driverNeeded, daysRent);

          ReceiptEntity resultReceiptEntity = new ReceiptEntity(receiptEntity);
          receipt.setTotalPrice(resultReceiptEntity.getTotalPrice());
          receiptService.registerReceipt(receipt);
          request.setAttribute("car", car);
          request.setAttribute("receipt", receipt);
          getServletContext().getRequestDispatcher("/pages/car/rent-car-succeeded.jsp").forward(request, response);
        } else {
          request.setAttribute("car", car);
          getServletContext().getRequestDispatcher("/pages/car/car-is-unavailable.jsp").forward(request, response);
        }
      } else {
        request.setAttribute("carId", carId);
        getServletContext().getRequestDispatcher("/pages/car/no-such-car.jsp").forward(request, response);
      }
    } catch (DatabaseException e) {
      log.error(e);
      getServletContext().getRequestDispatcher("/pages/error.jsp").forward(request, response);
    }

  }

  private AbstractReceiptEntity applyAdditions(AbstractReceiptEntity receiptEntity, Receipt receipt, boolean needDriver, int daysRent) {
    receipt.setDriverNeeded(needDriver);
    if (needDriver) {
      receiptEntity = new DriverAddition(receiptEntity);
    }
    receiptEntity = new DaysRentAddition(receiptEntity, daysRent);
    receipt.setDaysNumber(daysRent);
    return receiptEntity;
  }


}
