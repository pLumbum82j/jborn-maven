package ru.maven.jborn;

import ru.maven.jborn.terminal.UserTerminal;

import java.util.Scanner;

public class View {
    private UserTerminal userTerminal = null;

    public void mainMenu(){
        while (true) {
            System.out.println();
            System.out.println("Что вы хотите сделать:");
            System.out.println("Зарегистрироваться - нажмите '1'");
            System.out.println("Залогиниться - нажмите '2'");
            System.out.println("Выход из программы - нажмите '0'");
            Scanner scanner = new Scanner(System.in);;
            switch (scanner.nextInt()) {
                case (1):
                    System.out.println("Вы выбрали = Зарегистрироваться");
                    userTerminal = new UserTerminal();
                    userTerminal.createUser();
                    break;
                case (2):
                    System.out.println("Вы выбрали = Залогиниться");
                    userTerminal = new UserTerminal();
                    userTerminal.getUser();
//                    inLogin = getLogin();
//                    inPassword = getPassword();
//                    getAccountsUser(inLogin, inPassword);
                    break;
                case(3):
                    System.out.println("Вы выбрали = Найти пользователя по id");
                    userTerminal = new UserTerminal();
                    System.out.println(userTerminal.getUserById());
                    mainMenu();
                case (0):
                    return;
                default:
                    System.out.println("Такого варианта выбора нет");
            }
        }
    }

}
