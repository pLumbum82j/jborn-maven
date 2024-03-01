package ru.maven.jborn.terminal;

import java.util.Scanner;

public class View {
    private UserTerminal userTerminal = new UserTerminal();

    public void mainMenu() {
        while (true) {
            System.out.println();
            System.out.println("Что вы хотите сделать:");
            System.out.println("[1] Зарегистрироваться");
            System.out.println("[2] Залогиниться");
            System.out.println("[3] Найти пользователя по id");
            System.out.println("[0] Выход из программы");
            Scanner scanner = new Scanner(System.in);
            ;
            switch (scanner.nextInt()) {
                case (1):
                    System.out.println("Вы выбрали = Зарегистрироваться");
                    System.out.println("-----------------------------------");
                    userTerminal.createUser();
                   //mainMenu();
                    break;
                case (2):
                    System.out.println("Вы выбрали = Залогиниться");
                    System.out.println("-----------------------------------");
                    userTerminal.getUser();
                    break;
                case (3):
                    System.out.println("Вы выбрали = Найти пользователя по id");
                    System.out.println("-----------------------------------");
                    userTerminal.getUserById();
                     mainMenu();
                case (0):
                    return;
                default:
                    System.out.println("Такого варианта выбора нет");
            }
        }
    }

}
