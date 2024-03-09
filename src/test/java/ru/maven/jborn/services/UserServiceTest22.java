//package ru.maven.jborn.services;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.maven.jborn.dao.domain.UserDao;
//import ru.maven.jborn.mappers.UserMapper;
//import ru.maven.jborn.models.User;
//import ru.maven.jborn.models.dto.UserDto;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.Matchers.anyInt;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class UserServiceTest {
//    UserService userService;
//    UserMapper userMapper;
//    UserDao userDao;
//    User user;
//    UserDto userDto;
//
//    @BeforeEach
//    void setUp() {
//        userService = ServiceFactory.getUserService();
//        userMapper = mock(UserMapper.class);
//        userDao = mock(UserDao.class);
//        user = new User();
//        userDto = new UserDto();
//        user.setId(1);
//        user.setFirstName("Iliya");
//        user.setLastName("Smirnov");
//        user.setLogin("plumbum1");
//        user.setEmail("pb8d2@mail.ru");
//        userDto.setId(1);
//        userDto.setFirstName("Iliya");
//        userDto.setLastName("Smirnov");
//        userDto.setLogin("plumbum1");
//        userDto.setEmail("pb8d2@mail.ru");
//    }
//
//    @Test
//    void createUserSuccess() {
//        int r = 0;
//        when(userDao.duplicateCheck(user)).thenReturn(r);
//        when(userDao.insert(user)).thenReturn(user);
//        when(userMapper.userToUserDto(user)).thenReturn(userDto);
//
//
//        UserDto resultUser = userService.createUser("Iliya", "Smirnov", "plumbum1", "pb8d2@mail.ru", "1");
//
//        assertEquals(resultUser.getFirstName(), user.getFirstName());
//        assertEquals(resultUser.getLogin(), user.getLogin());
//
//    }
//
//    @Test
//    void createUserDuplicate() {
//        when(userDao.duplicateCheck(user)).thenReturn(anyInt());
//        when(userMapper.userToUserDto(null)).thenReturn(new UserDto());
//
//        UserDto resultUser = userService.createUser("Iliya", "Smirnov", "plumbum", "pb82@mail.ru", "1");
//
//        assertNull(resultUser.getLogin());
//
//    }
//
//    @Test
//    void getUser() {
//    }
//
//    @Test
//    void getUserById() {
//    }
//}