package ru.maven.jborn.services;

import org.apache.commons.codec.digest.DigestUtils;
import ru.maven.jborn.dao.domain.UserDao;
import ru.maven.jborn.mappers.UserMapper;
import ru.maven.jborn.models.User;
import ru.maven.jborn.models.dto.UserDto;

public class UserService {

    private final UserMapper userMapper;
    private final UserDao userDao;

    public UserService(UserMapper userMapper, UserDao userDao) {
        this.userMapper = userMapper;
        this.userDao = userDao;
    }

    public UserDto createUser(String firstName, String lastName, String login, String email, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(DigestUtils.md5Hex(password));
        return userDao.duplicateCheck(user) > 0 ?
                userMapper.userToUserDto(null) : userMapper.userToUserDto(userDao.insert(user));
    }

    public UserDto getUser(String login, String password) {
        User user = userDao.getUser(login, password);
        return user.getId() == null ? new UserDto() : userMapper.userToUserDto(user);

    }

    public UserDto getUserById(Integer id) {
        return userMapper.userToUserDto(userDao.findById(id));
    }
}
