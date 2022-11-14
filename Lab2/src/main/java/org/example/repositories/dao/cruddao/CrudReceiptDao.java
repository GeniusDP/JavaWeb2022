package org.example.repositories.dao.cruddao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.example.entities.receipt.Receipt;
import org.example.repositories.dao.extractors.Extractor;
import org.example.repositories.dao.extractors.ReceiptExtractor;
import org.example.repositories.dbutils.ConnectionPool;

public class CrudReceiptDao extends AbstractCrudDao<Receipt, Long> {

  private final Extractor<Receipt> receiptExtractor;

  public CrudReceiptDao(ConnectionPool connectionPool) {
    super(connectionPool);
    this.receiptExtractor = new ReceiptExtractor();
  }

  @Override
  protected Receipt insertInternal(Receipt receipt, Connection connection) throws SQLException {
    String sql = """
          insert into lab_java.receipts(user_id, car_id, driver_needed, days_number, total_price)
          values(?, ?, ?, ?, ?)
        """;
    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

    statement.setLong(1, receipt.getUser().getId());
    statement.setLong(2, receipt.getCar().getId());
    statement.setBoolean(3, receipt.getDriverNeeded());
    statement.setInt(4, receipt.getDaysNumber());
    statement.setInt(5, receipt.getTotalPrice());

    statement.executeUpdate();

    ResultSet generatedKeys = statement.getGeneratedKeys();
    if (generatedKeys.next()) {
      receipt.setId(generatedKeys.getLong(1));
    } else {
      throw new SQLException("Creating receipt failed, no ID obtained.");
    }
    generatedKeys.close();

    statement.close();
    return receipt;
  }

  @Override
  protected int deleteInternal(Long key, Connection connection) throws SQLException {
    String sql = "delete from lab_java.receipts where id = ?;";
    int rowsUpdated = 0;
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, key);
      rowsUpdated = statement.executeUpdate();
    }
    return rowsUpdated;
  }

  @Override
  protected Receipt updateInternal(Long key, Receipt newValue, Connection connection)
      throws SQLException {
    throw new IllegalArgumentException("not implemented method");
  }

  @Override
  protected List<Receipt> findAllInternal(Connection connection) throws SQLException {
    String sql = "select * from lab_java.receipts;";
    List<Receipt> receipts = new ArrayList<>();
    try (PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        receipts.add(receiptExtractor.extract(resultSet));
      }
    }
    return receipts;
  }

  @Override
  protected Receipt findByIdInternal(Long key, Connection connection) throws SQLException {
    String sql = "select * from lab_java.receipts where id = ?;";

    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, key);

    Receipt receipt = null;
    ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
      receipt = receiptExtractor.extract(resultSet);
    }
    resultSet.close();

    statement.close();
    return receipt;
  }

}
