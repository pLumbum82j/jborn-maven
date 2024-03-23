//package ru.maven.jborn.mappers;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.maven.jborn.models.User;
//import ru.maven.jborn.models.dto.UserDto;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class UserMapperTest {
//    UserMapper userMapper;
//    User user;
//
//    @BeforeEach
//    void setUp() {
//        userMapper = new UserMapper();
//        user = new User();
//    }
//
//    @Test
//    void userToUserDtoSuccess() {
//        user.setId(1);
//        user.setFirstName("Iliya");
//        user.setLastName("Smirnov");
//        user.setLogin("plumbum");
//        user.setEmail("pb82@mail.ru");
//
//        UserDto resultUser = userMapper.userToUserDto(user);
//
//        assertEquals(resultUser.getId(), user.getId());
//        assertEquals(resultUser.getFirstName(), user.getFirstName());
//        assertEquals(resultUser.getLastName(), user.getLastName());
//        assertEquals(resultUser.getLogin(), user.getLogin());
//        assertEquals(resultUser.getEmail(), user.getEmail());
//    }
//
//    @Test
//    void userToUserDtoFieldsNull() {
//        UserDto resultUser = userMapper.userToUserDto(user);
//
//        assertEquals(resultUser.getId(), user.getId());
//        assertEquals(resultUser.getFirstName(), user.getFirstName());
//        assertEquals(resultUser.getLastName(), user.getLastName());
//        assertEquals(resultUser.getLogin(), user.getLogin());
//        assertEquals(resultUser.getEmail(), user.getEmail());
//    }
//}