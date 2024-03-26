package ru.maven.jborn.dao.domain;

import ru.maven.jborn.dao.Dao;
import ru.maven.jborn.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User, Integer> {

    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(Integer id) {
        User resultUser = new User();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select id, first_name, last_name, login, email, password from users where id = ?");
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultUser;
    }

    @Override
    public List<User> findByAll() {
        List<User> resultListAllUsers = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select id, first_name, last_name, login, email, password from users");
            while (rs.next()) {
                User foundUser = new User();
                foundUser.setId(rs.getInt("id"));
                foundUser.setFirstName(rs.getString("first_name"));
                foundUser.setLastName(rs.getString("last_name"));
                foundUser.setLogin(rs.getString("login"));
                foundUser.setEmail(rs.getString("email"));
                resultListAllUsers.add(foundUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultListAllUsers;
    }

    @Override
    public User insert(User user) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection
                    .prepareStatement("insert into users(first_name, last_name, login, email, password) VALUES (?,?,?,?,?)");
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getLogin());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.executeUpdate();

            PreparedStatement psGetId = connection.prepareStatement("select id from users where login = ?");
            psGetId.setString(1, user.getLogin());
            ResultSet rs = psGetId.executeQuery();

            while (rs.next()) {
                user.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        boolean result = false;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("delete from users where id = ?");
            ps.setInt(1, id);
            int check = ps.executeUpdate();
            if (check == 1) {
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Integer duplicateCheck(User user) {
        int result = 0;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select count(*) as count from users where login = ? or email = ?");
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getEmail());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public User getUser(String login, String password) {
        User user = new User();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select id, first_name, last_name, login, email, password from users where login = ? and password = ?");
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setLogin(rs.getString("login"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
