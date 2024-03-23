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
//import static org.mockito.Matchers.anyObject;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class UserServiceTestJUnin5 {
//    UserService userService;
//
//    UserMapper userMapper;
//
//    UserDao userDao;
//    User user;
//    UserDto userDto;
//
//    @BeforeEach
//    void setUp() {
//        userDao = mock(UserDao.class);
//        userMapper = mock(UserMapper.class);
//        userService = new UserService(userMapper, userDao);
//        user = new User();
//        userDto = new UserDto();
//        user.setFirstName("Iliya");
//        user.setLastName("Smirnov");
//        user.setLogin("plumbum1");
//        user.setEmail("pb8d2@mail.ru");
//        user.setPassword("c4ca4238a0b923820dcc509a6f75849b");
//        userDto.setId(1);
//        userDto.setFirstName("Iliya");
//        userDto.setLastName("Smirnov");
//        userDto.setLogin("plumbum1");
//        userDto.setEmail("pb8d2@mail.ru");
//    }
//
//    @Test
//    void createUserSuccess() {
//        when(userDao.duplicateCheck(user)).thenReturn(0);
//        when(userDao.insert(user)).thenReturn(anyObject());
//        when(userMapper.userToUserDto(user)).thenReturn(userDto);
//
//        UserDto resultUser = userService.createUser("Iliya", "Smirnov", "plumbum1", "pb8d2@mail.ru", "1");
//
//        assertEquals(resultUser.getFirstName(), user.getFirstName());
//        assertEquals(resultUser.getLogin(), user.getLogin());
//    }
//
//    @Test
//    void createUserDuplicate() {
//        when(userDao.duplicateCheck(user)).thenReturn(1);
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
//    void getUserByIdSuccess() {
//        Integer id = 1;
//        when(userDao.findById(id)).thenReturn(anyObject());
//        when(userMapper.userToUserDto(user)).thenReturn(userDto);
//
//        UserDto resultUser = userService.getUserById(id);
//    }
//}