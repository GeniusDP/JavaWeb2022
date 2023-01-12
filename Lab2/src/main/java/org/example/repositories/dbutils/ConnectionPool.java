package org.example.repositories.dbutils;

import static java.lang.System.exit;
import static java.lang.System.in;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class ConnectionPool {

  private final static int DEFAULT_POOLS_NUMBER = 10;
  private final String url;
  private final Vector<Connection> availableConnections;
  private final Vector<Connection> usedConnections;

  private ConnectionPool(String url, String driverClassFullName, int initConnCnt) {
    try {
      Class.forName(driverClassFullName);
    } catch (ClassNotFoundException e) {
      System.err.println(e.getMessage());
      exit(1337);
    }
    this.url = url;

    availableConnections = new Vector<>();
    usedConnections = new Vector<>();
    for (int i = 0; i < initConnCnt; i++) {
      availableConnections.add(createNewConnection());
    }
  }

  private static ConnectionPool instance;

  public static ConnectionPool getInstance() {
    if(instance == null){
      Properties properties = new Properties();
      try {
        properties.load(new FileReader("src/main/resources/application.properties"));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      String url = properties.getProperty("database.url");
      instance = new ConnectionPool(url, "org.postgresql.Driver");
    }
    return instance;
  }

  private ConnectionPool(String url, String driverClassFullName) {
    try {
      Class.forName(driverClassFullName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.url = url;

    availableConnections = new Vector<>();
    usedConnections = new Vector<>();
    for (int i = 0; i < DEFAULT_POOLS_NUMBER; i++) {
      availableConnections.add(createNewConnection());
    }
  }

  private Connection createNewConnection() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url);
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      exit(1337);
    }
    return conn;
  }

  public synchronized Connection getConnection() {
    if (availableConnections.size() == 0) {
      Connection newConn = createNewConnection();
      usedConnections.addElement(newConn);
      return newConn;
    }
    Connection newConn = availableConnections.lastElement();
    availableConnections.removeElement(newConn);
    usedConnections.addElement(newConn);
    return newConn;
  }

  public synchronized void putBack(Connection c) throws NullPointerException {
    if (c != null) {
      if (usedConnections.removeElement(c)) {
        availableConnections.addElement(c);
      } else {
        throw new IllegalArgumentException("Connection not from this connection pool");
      }
    }
  }

}
