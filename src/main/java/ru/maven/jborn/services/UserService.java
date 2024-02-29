package ru.maven.jborn.services;

import ru.maven.jborn.User;
import ru.maven.jborn.dao.domain.UserDao;
import ru.maven.jborn.dto.UserDto;
import ru.maven.jborn.mappers.UserMapper;

import java.sql.SQLException;

public class UserService {
    UserDao userDao = UserDao.getUserDao();

    public UserDto getUserById(Integer id) {
        User user = null;
        UserDto userDto = null;
        UserMapper userMapper = new UserMapper();
        try {
           // user = userDao.findById(id);
            userDto = userMapper.userToUserDto(userDao.findById(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       // System.out.println(user);
        return userDto;
    }

}
