package ru.maven.jborn.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.maven.jborn.dao.domain.UserDao;
import ru.maven.jborn.mappers.UserMapper;
import ru.maven.jborn.models.User;
import ru.maven.jborn.models.dto.UserDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserMapper userMapper;
    @Mock
    UserDao userDao;
    @Mock
    User user;
    @Mock
    UserDto userDto;

    @Before
    public void setUp() {
        //userMapper = new UserMapper();
        user.setId(1);
        user.setFirstName("Iliya");
        user.setLastName("Smirnov");
        user.setLogin("plumbum1");
        user.setEmail("pb8d2@mail.ru");
        userDto.setId(1);
        userDto.setFirstName("Iliya");
        userDto.setLastName("Smirnov");
        userDto.setLogin("plumbum1");
        userDto.setEmail("pb8d2@mail.ru");
    }

    @Test
    public void createUser() {
        when(userDao.duplicateCheck(user)).thenReturn(0);
        when(userDao.insert(user)).thenReturn(anyObject());
        when(userMapper.userToUserDto(user)).thenReturn(userDto);

        UserDto resultUser = userService.createUser("Iliya", "Smirnov", "plumbum1", "pb8d2@mail.ru", "1");

        assertEquals(resultUser.getFirstName(), user.getFirstName());
        assertEquals(resultUser.getLogin(), user.getLogin());
    }

    @Test
    public void createUserDuplicate() {
        int r = 1;
        when(userDao.duplicateCheck(user)).thenReturn(1);
        when(userMapper.userToUserDto(null)).thenReturn(new UserDto());

        UserDto resultUser = userService.createUser("Iliya", "Smirnov", "plumbum", "pb82@mail.ru", "1");

        assertNull(resultUser.getLogin());

    }

    @Test
    public void getUser() {
    }

    @Test
    public void getUserById() {
    }
}