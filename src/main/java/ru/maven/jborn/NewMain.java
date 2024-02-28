package ru.maven.jborn;

import ru.maven.jborn.dao.domain.UserDao;

import java.sql.SQLException;

public class NewMain {
    public static void main(String[] args) {
        UserDao userDao = UserDao.getUserDao();
        try {
           User user = userDao.findById(1);
            System.out.println(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
