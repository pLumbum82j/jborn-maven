package ru.maven.jborn.services;

import org.apache.commons.codec.digest.DigestUtils;
import ru.maven.jborn.dao.domain.UserDao;
import ru.maven.jborn.mappers.UserMapper;
import ru.maven.jborn.models.User;
import ru.maven.jborn.models.dto.UserDto;

import java.sql.SQLException;

public class UserService {
    UserMapper userMapper = new UserMapper();
    UserDao userDao = UserDao.getUserDao();

    public UserDto createUser(String firstName, String lastName, String login, String email, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(DigestUtils.md5Hex(password));
        if (userDao.duplicateCheck(user) > 0) {
            return userMapper.userToUserDto(user);
        } else {
            return userMapper.userToUserDto(userDao.insert(user));
        }
    }

    public void getUser() {
        userDao.delete(17);
    }

    public UserDto getUserById(Integer id) {
        UserDto userDto;
        try {
            userDto = userMapper.userToUserDto(userDao.findById(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userDto;
    }


}
