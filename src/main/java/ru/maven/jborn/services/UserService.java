package ru.maven.jborn.services;

import org.apache.commons.codec.digest.DigestUtils;
import ru.maven.jborn.dao.domain.UserDao;
import ru.maven.jborn.mappers.UserMapper;
import ru.maven.jborn.models.User;
import ru.maven.jborn.models.dto.UserDto;

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

    public User getUser(String login, String password) {
        return userDao.getUser(login, password);
    }

    public UserDto getUserById(Integer id) {
        UserDto userDto;
        userDto = userMapper.userToUserDto(userDao.findById(id));
        return userDto;
    }


}
