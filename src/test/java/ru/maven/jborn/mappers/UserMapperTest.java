package ru.maven.jborn.mappers;

import org.junit.Before;
import org.junit.Test;
import ru.maven.jborn.models.User;
import ru.maven.jborn.models.dto.UserDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserMapperTest {
    UserMapper userMapper;
    User user;

    @Before
    public void setUp() {
        userMapper = new UserMapper();
        user = new User();
    }

    @Test
    public void userToUserDto_Success() {
        user.setId(1);
        user.setFirstName("Iliya");
        user.setLastName("Smirnov");
        user.setLogin("plumbum");
        user.setEmail("pb82@mail.ru");

        UserDto result = userMapper.userToUserDto(user);

        assertEquals(result.getId(), user.getId());
        assertEquals(result.getFirstName(), user.getFirstName());
        assertEquals(result.getLastName(), user.getLastName());
        assertEquals(result.getLogin(), user.getLogin());
        assertEquals(result.getEmail(), user.getEmail());
    }

    @Test
    public void userToUserDto_FieldsNull() {
        UserDto result = userMapper.userToUserDto(null);

        assertNull(result.getId());
        assertNull(result.getFirstName());
        assertNull(result.getLastName());
        assertNull(result.getLogin());
        assertNull(result.getEmail());
    }
}