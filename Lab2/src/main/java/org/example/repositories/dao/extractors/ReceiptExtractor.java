package org.example.repositories.dao.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.entities.car.Car;
import org.example.entities.receipt.Receipt;
import org.example.entities.receipt.ReceiptStatus;
import org.example.entities.user.User;

public class ReceiptExtractor implements Extractor<Receipt> {

  @Override
  public Receipt extract(ResultSet resultSet) throws SQLException {
    Long id = resultSet.getObject("id", Long.class);
    Long userId = resultSet.getObject("user_id", Long.class);
    Long carId = resultSet.getObject("car_id", Long.class);
    ReceiptStatus status = ReceiptStatus.valueOf(resultSet.getObject("status", String.class));
    Boolean driverNeeded = resultSet.getObject("driver_needed", Boolean.class);
    Integer daysNumber = resultSet.getObject("days_number", Integer.class);
    Integer totalPrice = resultSet.getObject("total_price", Integer.class);
    String declineMessage = resultSet.getObject("decline_message", String.class);
    Car car = Car.builder()
        .id(carId)
        .build();
    User user = User.builder()
        .id(userId)
        .build();
    return Receipt.builder()
        .id(id)
        .user(user)
        .car(car)
        .driverNeeded(driverNeeded)
        .status(status)
        .daysNumber(daysNumber)
        .totalPrice(totalPrice)
        .declineMessage(declineMessage)
        .build();
  }
}
