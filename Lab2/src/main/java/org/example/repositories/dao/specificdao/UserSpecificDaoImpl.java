package org.example.repositories.dao.specificdao;

import lombok.RequiredArgsConstructor;
import org.example.entities.Mark;
import org.example.entities.User;
import org.example.exceptions.DatabaseException;
import org.example.repositories.dao.extractors.Extractor;
import org.example.repositories.dao.extractors.MarkExtractor;
import org.example.repositories.dao.extractors.UserExtractor;
import org.example.repositories.dbutils.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSpecificDaoImpl implements UserSpecificDao {

    private final ConnectionPool connectionPool;
    private final Extractor<User> userExtractor;

    public UserSpecificDaoImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.userExtractor = new UserExtractor();
    }

    @Override
    public User findByUsername(String username) {
        if(username == null){
            throw new IllegalArgumentException("username must not be null");
        }
        Connection connection = connectionPool.getConnection();

        String sql = "select * from lab_java.car_users where username = ?;";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return userExtractor.extract(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionPool.putBack(connection);
        }
        return null;
    }

}
