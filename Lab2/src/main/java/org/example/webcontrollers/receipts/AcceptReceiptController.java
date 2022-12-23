
package org.example.webcontrollers.receipts;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
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
import org.example.services.ReceiptService;

@Log4j
@WebServlet(name = "AcceptReceiptController", urlPatterns = "/menu/accept-receipt")
public class AcceptReceiptController extends HttpServlet {

  private ReceiptService receiptService;

  @Override
  public void init() {
    CrudMarkDao crudMarkDao = new CrudMarkDao(ConnectionPool.getInstance());
    MarkSpecificDaoImpl markSpecificDao = new MarkSpecificDaoImpl(ConnectionPool.getInstance());
    MarkRepository markRepository = new MarkRepository(crudMarkDao, markSpecificDao);
    CarSpecificDao carSpecificDao = new CarSpecificDaoImpl(ConnectionPool.getInstance());
    CrudCarDao crudCarDao = new CrudCarDao(ConnectionPool.getInstance());

    CarRepository carRepository = new CarRepository(crudCarDao, markRepository, carSpecificDao);

    CrudUserDao userDao = new CrudUserDao(ConnectionPool.getInstance());
    UserSpecificDaoImpl userSpecificDao = new UserSpecificDaoImpl(ConnectionPool.getInstance());
    UserRepository userRepository = new UserRepository(userDao, userSpecificDao);
    CrudReceiptDao crudReceiptDao = new CrudReceiptDao(ConnectionPool.getInstance());
    ReceiptSpecificDao receiptSpecificDao = new ReceiptSpecificDao(ConnectionPool.getInstance(), userRepository, carRepository);
    ReceiptRepository receiptRepository = new ReceiptRepository(crudReceiptDao, receiptSpecificDao, carRepository, userRepository);
    receiptService = new ReceiptService(receiptRepository);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      long receiptId = Long.parseLong(request.getParameter("receiptId"));
      if (receiptService.acceptCar(receiptId)) {
        response.sendRedirect("/menu/all-receipts");
        return;
      }
      getServletContext().getRequestDispatcher("/pages/receipts/failed-to-accept-car.jsp").forward(request, response);
    } catch (DatabaseException e) {
      log.error(e);
      getServletContext().getRequestDispatcher("/pages/error.jsp").forward(request, response);
    }

  }

}
