package org.example.repositories.dao.specificdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.example.entities.car.Car;
import org.example.entities.receipt.Receipt;
import org.example.entities.user.User;
import org.example.exceptions.DatabaseException;
import org.example.repositories.CarRepository;
import org.example.repositories.UserRepository;
import org.example.repositories.dao.extractors.Extractor;
import org.example.repositories.dao.extractors.ReceiptExtractor;
import org.example.repositories.dbutils.ConnectionPool;

public class ReceiptSpecificDao {

  private final ConnectionPool connectionPool;
  private final Extractor<Receipt> receiptExtractor;
  private final UserRepository userRepository;
  private final CarRepository carRepository;

  public ReceiptSpecificDao(ConnectionPool connectionPool, UserRepository userRepository,
      CarRepository carRepository) {
    this.connectionPool = connectionPool;
    this.userRepository = userRepository;
    this.carRepository = carRepository;
    this.receiptExtractor = new ReceiptExtractor();
  }

  public List<Receipt> getReceiptsOfUserByUserId(Long userId) {
    Connection connection = connectionPool.getConnection();

    String sql = """
        select * from lab_java.receipts where user_id = ?;
        """;
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setLong(1, userId);
      return extractList(statement);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    } finally {
      connectionPool.putBack(connection);
    }
  }

  public List<Receipt> getReturnedAndDeclinedReceipts() {
    Connection connection = connectionPool.getConnection();

    String sql = """
        select * from lab_java.receipts where status
          IN ('DECLINED', 'RETURNED', 'RETURNED_WITH_DAMAGE');
        """;
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      return extractList(statement);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    } finally {
      connectionPool.putBack(connection);
    }
  }


  public List<Receipt> getRegisteredOrAcceptedReceipts() {
    Connection connection = connectionPool.getConnection();

    String sql = """
        select * from lab_java.receipts where status IN ('REGISTERED', 'ACCEPTED');
        """;
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      return extractList(statement);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    } finally {
      connectionPool.putBack(connection);
    }
  }


  private List<Receipt> extractList(PreparedStatement statement) throws SQLException {
    List<Receipt> receipts = new ArrayList<>();
    ResultSet resultSet = statement.executeQuery();
    while (resultSet.next()) {
      receipts.add(receiptExtractor.extract(resultSet));
    }
    return receipts.stream().peek(receipt -> {
      User user = receipt.getUser();
      Car car = receipt.getCar();
      if (user != null && user.getId() != null) {
        receipt.setUser(userRepository.findById(user.getId()));
      }
      if (car != null && car.getId() != null) {
        receipt.setCar(carRepository.findById(car.getId()));
      }
    }).collect(Collectors.toList());
  }


}
