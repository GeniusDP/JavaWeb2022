package org.example.repositories.dao.cruddao;

import org.example.entities.Mark;
import org.example.entities.User;
import org.example.repositories.dao.extractors.Extractor;
import org.example.repositories.dao.extractors.UserExtractor;
import org.example.repositories.dbutils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudUserDao extends AbstractCrudDao<User, Long> {

    private final Extractor<User> userExtractor;

    public CrudUserDao(ConnectionPool connectionPool) {
        super(connectionPool);
        this.userExtractor = new UserExtractor();
    }

    @Override
    protected User insertInternal(User user, Connection connection) throws SQLException {
        String sql = "insert into car_users(username, password, first_name, last_name, role) values (?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getFirstName());
        statement.setString(4, user.getLastName());
        statement.setString(5, user.getRole().name());
        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            user.setId(generatedKeys.getLong(1));
        } else {
            throw new SQLException("Creating user failed, no ID obtained.");
        }

        generatedKeys.close();
        statement.close();

        return user;
    }

    @Override
    protected int deleteInternal(Long key, Connection connection) throws SQLException {
        throw new IllegalArgumentException("not implemented method");
    }

    @Override
    protected User updateInternal(Long key, User newValue, Connection connection) throws SQLException {
        throw new IllegalArgumentException("not implemented method");
    }

    @Override
    protected List<User> findAllInternal(Connection connection) throws SQLException {
        String sql = "select * from car_users;";
        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(userExtractor.extract(resultSet));
            }
        }

        return users;
    }

    @Override
    protected User findByIdInternal(Long key, Connection connection) throws SQLException {
        String sql = "select * from car_users where id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, key);

        User user = null;
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = userExtractor.extract(resultSet);
        }
        resultSet.close();
        statement.close();

        return user;
    }
}
