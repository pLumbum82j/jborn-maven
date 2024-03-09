package ru.maven.jborn.mappers;

import ru.maven.jborn.models.User;
import ru.maven.jborn.models.dto.UserDto;

public class UserMapper {
    public UserDto userToUserDto(User user) {
        UserDto resultUserDto = new UserDto();
        if (user == null) {
            return resultUserDto;
        }
        resultUserDto.setId(user.getId());
        resultUserDto.setFirstName(user.getFirstName());
        resultUserDto.setLastName(user.getLastName());
        resultUserDto.setLogin(user.getLogin());
        resultUserDto.setEmail(user.getEmail());
        return resultUserDto;
    }
}
