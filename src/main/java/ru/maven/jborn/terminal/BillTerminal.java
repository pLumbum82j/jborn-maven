package ru.maven.jborn.terminal;

import ru.maven.jborn.models.User;
import ru.maven.jborn.models.dto.BillDto;
import ru.maven.jborn.services.BillService;

import java.util.Scanner;

public class BillTerminal {
    BillService billService = new BillService();
    Scanner scanner = new Scanner(System.in);

    public void menuBill(User user) {
        while (true) {
            while (true) {
                System.out.println();
                System.out.println("Что вы хотите сделать:");
                System.out.println("[1] Создать счёт");
                System.out.println("[2] Посмотреть все счета");
                System.out.println("[3] Удалить счёт");
                System.out.println("[0] Выйти из меню");
                switch (scanner.nextInt()) {
                    case (1):
                        System.out.println("Вы выбрали = Создать счёт");
                        System.out.println("-----------------------------------");
                        BillDto billDto = billService.createBill(user);
                        System.out.println("Создан счёт для пользователя: " + user.getLogin());
                        System.out.println(billDto);
                        break;
                    case (0):
                        return;
                    default:
                        System.out.println("Такого варианта выбора нет");
                }
            }
        }
    }
}
