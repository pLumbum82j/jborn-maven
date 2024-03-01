package ru.maven.jborn;

import ru.maven.jborn.terminal.View;

public class NewMain {
    static View view = new View();

    public static void main(String[] args) {
        view.mainMenu();
    }
//    public static void main(String[] args) {
//        UserDao userDao = UserDao.getUserDao();
//        try {
//           User user = userDao.findById(1);
//            System.out.println(user);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
