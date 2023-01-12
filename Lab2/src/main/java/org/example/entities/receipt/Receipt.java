package org.example.entities.receipt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.entities.car.Car;
import org.example.entities.user.User;

@Data
@Builder
@AllArgsConstructor
public class Receipt {

  private Long id;

  private User user;

  private Car car;

  @Builder.Default
  private ReceiptStatus status = ReceiptStatus.REGISTERED;

  private Boolean driverNeeded;

  private Integer daysNumber;

  private Integer totalPrice;

  private String declineMessage;

  @Override
  public String toString() {
    return String.format("Here is your Receipt.\nunique id   : %d, \nfirst name  : %s, \n"
        + "last name   : %s, \ncar name    : %s, \ncar model   : %s, \nfor days    : %d,"
        + "\ndriver need : %b, \nstatus      : %s, \ntotal price : %d$" , this.id, user.getFirstName(), user.getLastName(), car.getName(), car.getMark().getName(),
        daysNumber, driverNeeded, status.name(), totalPrice );
  }
}
