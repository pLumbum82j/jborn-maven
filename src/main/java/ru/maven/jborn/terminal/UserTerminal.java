package ru.maven.jborn.terminal;

import org.apache.commons.codec.digest.DigestUtils;
import ru.maven.jborn.models.dto.UserDto;
import ru.maven.jborn.services.UserService;

import java.util.Arrays;
import java.util.Scanner;

public class UserTerminal {
    UserService userService = new UserService();
    Scanner scanner = new Scanner(System.in);
    String inLogin;
    String inPassword;
    String inFirstName;
    String inLastName;
    String inEmail;

    public void createUser() {
        System.out.println("Введите логин");
        inLogin = checkNull(scanner.nextLine());
        System.out.println("Введите имя пользователя");
        inFirstName = checkNull(scanner.nextLine());
        System.out.println("Введите фамилию пользователя");
        inLastName = scanner.nextLine();
        System.out.println("Введите email");
        inEmail = checkEmail(checkNull(scanner.nextLine()));
        System.out.println("Введите пароль");
        inPassword = checkNull(scanner.nextLine());
        UserDto userDto = userService.createUser(inFirstName, inLastName, inLogin, inEmail, inPassword);
        if (userDto.getId() == null) {
            System.out.println("Такой пользователь существует");
        } else {
            System.out.println("Пользователь создан: " + userDto);
        }
    }

    public void getUser() {
        System.out.println("Введите логин:");
        inLogin = checkNull(scanner.nextLine());
        System.out.println("Введите пароль:");
        inPassword = checkNull(DigestUtils.md5Hex(scanner.nextLine()));
        //Реализовать
        userService.getUser();
    }

    public void getUserById() {
        System.out.println("Введите id пользователя");
        UserDto userDto = userService.getUserById(scanner.nextInt());
        if (userDto.getId() == null) {
            System.out.println("Такой пользователь не существует");
        } else {
            System.out.println("Пользователь найден: " + userDto);
        }
    }


    private String checkNull(String string) {
        if (string.isEmpty() || string.isBlank()) {
            System.out.println("Поле не может быть пустым");
            string = scanner.nextLine();
            checkNull(string);
        }
        return string;
    }

    private String checkEmail(String string) {
        String check = string;
        long result = Arrays.stream(check.split("")).filter(s -> s.equals("@")).count();
        if (result == 0 || result > 1) {
            System.out.println("Вы ввели неправильно эл.почту");
            check = checkEmail(scanner.nextLine());
        }
        return check;
    }
}
