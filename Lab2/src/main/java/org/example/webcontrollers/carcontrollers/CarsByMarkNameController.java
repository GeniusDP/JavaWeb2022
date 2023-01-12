package org.example.webcontrollers.carcontrollers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import org.example.entities.car.Car;
import org.example.exceptions.DatabaseException;
import org.example.repositories.CarRepository;
import org.example.repositories.MarkRepository;
import org.example.repositories.dao.cruddao.CrudCarDao;
import org.example.repositories.dao.cruddao.CrudMarkDao;
import org.example.repositories.dao.specificdao.CarSpecificDao;
import org.example.repositories.dao.specificdao.CarSpecificDaoImpl;
import org.example.repositories.dao.specificdao.MarkSpecificDaoImpl;
import org.example.repositories.dbutils.ConnectionPool;
import org.example.services.CarsService;

@Log4j
@WebServlet(name = "CarsByMarkName", urlPatterns = "/menu/get-cars-by-mark-name")
public class CarsByMarkNameController extends HttpServlet {

  private CarsService carsService;

  @Override
  public void init() {
    CrudMarkDao crudMarkDao = new CrudMarkDao(ConnectionPool.getInstance());
    MarkSpecificDaoImpl markSpecificDao = new MarkSpecificDaoImpl(ConnectionPool.getInstance());
    MarkRepository markRepository = new MarkRepository(crudMarkDao, markSpecificDao);
    CarSpecificDao carSpecificDao = new CarSpecificDaoImpl(ConnectionPool.getInstance());
    CrudCarDao crudCarDao = new CrudCarDao(ConnectionPool.getInstance());

    CarRepository carRepository = new CarRepository(crudCarDao, markRepository, carSpecificDao);
    carsService = new CarsService(carRepository);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      String markName = req.getParameter("markName");
      if (markName != null) {
        List<Car> cars = carsService.getCarsByMark(markName);
        req.setAttribute("cars", cars);
      }
      req.setAttribute("caption", "Cars by mark name " + (markName != null ? markName : ""));
      req.setAttribute("description", "List of cars of mark " + (markName != null ? markName : ""));
      getServletContext().getRequestDispatcher("/pages/car/get-cars-by-mark-name.jsp").forward(req, resp);
    } catch (DatabaseException e) {
      log.error(e);
      getServletContext().getRequestDispatcher("/pages/error.jsp").forward(req, resp);
    }
  }
}
