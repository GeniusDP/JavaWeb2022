package org.example.webcontrollers.carcontrollers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.entities.car.Car;
import org.example.repositories.CarRepository;
import org.example.repositories.MarkRepository;
import org.example.repositories.dao.cruddao.CrudCarDao;
import org.example.repositories.dao.cruddao.CrudMarkDao;
import org.example.repositories.dao.specificdao.CarSpecificDao;
import org.example.repositories.dao.specificdao.CarSpecificDaoImpl;
import org.example.repositories.dao.specificdao.MarkSpecificDaoImpl;
import org.example.repositories.dbutils.ConnectionPool;
import org.example.services.CarsService;

@WebServlet(name = "CarByPriceController", urlPatterns = "/menu/get-cars-by-price")
public class CarsByPriceController extends HttpServlet {

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
    List<Car> cars = carsService.getCarsSortedByPrice();
    req.setAttribute("cars", cars);
    req.setAttribute("caption", "Cars by price");
    req.setAttribute("description", "List of cars, sorted by price");
    getServletContext().getRequestDispatcher("/pages/get-cars-list.jsp").forward(req, resp);
  }

}
