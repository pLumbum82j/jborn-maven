package ru.maven.jborn;

import org.apache.commons.codec.digest.DigestUtils;
import ru.maven.jborn.models.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    final static String JBORN = "jborn";
    final static String URL = "jdbc:postgresql://localhost:5432/jborn_finance";

    public static void main(String[] args) {
        mainMenu();
    }

    public static String getLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите логин:");
        return scanner.nextLine();
    }

    public static String getPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите пароль:");
        return DigestUtils.md5Hex(scanner.nextLine());
    }

    public static int getValueMainMenu() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static User createUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите Имя");
        String firstname = scanner.nextLine();
        System.out.println("Введите Фамилию");
        String lastname = scanner.nextLine();
        String login = getLogin();
        System.out.println("Введите Email");
        String email = scanner.nextLine();
        String password = getPassword();
        System.out.println("Сколько счетов пользователя создать?");
        int count = scanner.nextInt();
        Map<Integer, Integer> maps = new HashMap<>();
        for (int i = 0; i < count; i++) {
            System.out.println("Введите Номер счёта");
            Integer account = scanner.nextInt();
            System.out.println("Введите баланс");
            Integer value = scanner.nextInt();
            maps.put(account, value);
        }
        return new User(firstname, lastname, login, email, password, maps);

    }

    public static void mainMenu() {
        String inLogin;
        String inPassword;

        while (true) {
            System.out.println();
            System.out.println("Что вы хотите сделать:");
            System.out.println("Зарегистрироваться - нажмите '1'");
            System.out.println("Залогиниться - нажмите '2'");
            System.out.println("Выход из программы - нажмите '0'");
            int value = getValueMainMenu();
            switch (value) {
                case (1):
                    System.out.println("Вы выбрали = Зарегистрироваться");
                    createUserAndAccount(createUser());
                    break;
                case (2):
                    System.out.println("Вы выбрали = Залогиниться");
                    inLogin = getLogin();
                    inPassword = getPassword();
                    getAccountsUser(inLogin, inPassword);
                    break;
                case (0):
                    return;
                default:
                    System.out.println("Такого варианта выбора нет");
            }
        }
    }


    public static void createUserAndAccount(User user) {
        try (Connection connection = DriverManager.getConnection(URL,
                JBORN, JBORN)
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into users(first_name, last_name, login, email, password)\n" +
                            "VALUES (?, ?, ?, ?, ?);"
            );
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatementGetUser = connection.prepareStatement(
                    "SELECT u.id\n" +
                            "FROM users as u\n" +
                            "WHERE u.login = ? AND u.password = ?;"
            );
            preparedStatementGetUser.setString(1, user.getLogin());
            preparedStatementGetUser.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatementGetUser.executeQuery();
            int userId = 0;
            while (resultSet.next()) {
                userId = resultSet.getInt("id");
            }


            for (Map.Entry<Integer, Integer> maps : user.getAccount().entrySet()) {
                PreparedStatement preparedStatementAccount = connection.prepareStatement(
                        "insert into bill(number_accounts, user_id, values)\n" +
                                "VALUES (?, ?, ?);"
                );
                preparedStatementAccount.setInt(1, maps.getKey());
                preparedStatementAccount.setInt(2, userId);
                preparedStatementAccount.setInt(3, maps.getValue());
                preparedStatementAccount.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAccountsUser(String login, String pass) {
        try (Connection connection = DriverManager.getConnection(URL,
                JBORN, JBORN)
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT u.login, b.number_accounts, b.values\n" +
                            "FROM users as u\n" +
                            "JOIN bill b on u.id = b.user_id\n" +
                            "WHERE u.login = ? AND u.password = ?;"
            );
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pass);


            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.wasNull()) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("login") + "\t " +
                            resultSet.getString("number_accounts") + "\t " +
                            resultSet.getString("values")
                    );
                }
            } else {
                System.out.println("Такого пользователя нет в системе");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}