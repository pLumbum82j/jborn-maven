package ru.maven.jborn.dao.domain;

import ru.maven.jborn.User;
import ru.maven.jborn.dao.Dao;
import ru.maven.jborn.dao.DaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao implements Dao<User, Integer> {
    private static UserDao userDao;

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    private UserDao() {
    }

    @Override
    public User findById(Integer id) throws SQLException {
        User resultUser = null;
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from users where id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultUser = new User();
                resultUser.setId(rs.getInt("id"));
                resultUser.setFirstName(rs.getString("first_name"));
                resultUser.setLastName(rs.getString("last_name"));
                resultUser.setLogin(rs.getString("login"));
                resultUser.setEmail(rs.getString("email"));
            }
        }

        return resultUser;
    }

    @Override
    public List<User> findByAll() {
        return null;
    }

    @Override
    public User insert(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }
}
