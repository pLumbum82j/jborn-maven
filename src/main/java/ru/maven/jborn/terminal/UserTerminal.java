package ru.maven.jborn.terminal;

import org.apache.commons.codec.digest.DigestUtils;
import ru.maven.jborn.dto.UserDto;
import ru.maven.jborn.services.UserService;

import java.util.Scanner;

public class UserTerminal {
    UserService userService = new UserService();
    Scanner scanner = new Scanner(System.in);
    String inLogin;
    String inPassword;

    public void createUser() {

    }
    public UserDto getUserById(){
        System.out.println("Введите id пользователя");
        return userService.getUserById(scanner.nextInt());
    }
    public void getUser() {
        System.out.println("Введите логин:");
        inLogin = scanner.nextLine();
        System.out.println("Введите пароль:");
        inPassword = DigestUtils.md5Hex(scanner.nextLine());

    }
}
