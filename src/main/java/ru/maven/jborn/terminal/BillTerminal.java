package ru.maven.jborn.terminal;

import ru.maven.jborn.models.dto.BillDto;
import ru.maven.jborn.models.dto.UserDto;
import ru.maven.jborn.services.BillService;
import ru.maven.jborn.services.ServiceFactory;

import java.util.List;
import java.util.Scanner;

public class BillTerminal {
    BillService billService = ServiceFactory.getBillService();
    Scanner scanner = new Scanner(System.in);

    public void billMenu(UserDto user, String password) {
        while (true) {
            System.out.println();
            System.out.println("Что вы хотите сделать:");
            System.out.println("[1] Создать счёт");
            System.out.println("[2] Посмотреть все счета");
            System.out.println("[-3] Удалить счёт");
            System.out.println("[0] Выйти из меню");
            switch (scanner.nextInt()) {
                case (1):
                    System.out.println("Вы выбрали = Создать счёт");
                    System.out.println("-----------------------------------");
                    System.out.println("Введите имя счёта:");
                    Scanner scanner1 = new Scanner(System.in);
                    String nameAccount = checkNull(scanner1.nextLine());
                    BillDto billDto = billService.createBill(user, password, nameAccount);
                    if (billDto.getNameAccounts() == null) {
                        System.out.println("У пользователя более 5-и счетов, нельзя созать больше");
                    } else if (billDto.getNameAccounts().equals("duplicate")) {
                        System.out.println("Счёт с названием " + nameAccount + " уже сущесвует");
                    } else {
                        System.out.println("Создан счёт для пользователя: " + user.getLogin());
                        System.out.println(billDto);
                    }
                    break;
                case (2):
                    System.out.println("Вы выбрали = Посмотреть все счета");
                    System.out.println("-----------------------------------");
                    List<BillDto> billDtoList = billService.getBillAllUser(user, password);
                    if (billDtoList.isEmpty()) {
                        System.out.println("У пользователя нет счетов");
                    } else {
                        System.out.println(billDtoList);
                    }
                    break;
                case (3):
                    System.out.println("Вы выбрали = Создать счёт");
                    System.out.println("-----------------------------------");
                    System.out.println("Введите имя счёта:");
                    Scanner scanner2 = new Scanner(System.in);
                    String nameAccountDelete = checkNull(scanner2.nextLine());
                    boolean result = billService.removeBillUser(user, password, nameAccountDelete);
                    if (result) {
                        System.out.println("Счёт " + nameAccountDelete + " удалён");
                    } else {
                        System.out.println("Счёта с наименованием " + nameAccountDelete + " несущесвует");
                    }
                    break;
                case (0):
                    return;
                default:
                    System.out.println("Такого варианта выбора нет");
            }
        }
    }

    private String checkNull(String string) {
        String check = string;
        if (string.isEmpty() || string.isBlank()) {
            System.out.println("Поле не может быть пустым");
            Scanner scanner1 = new Scanner(System.in);
            string = scanner1.nextLine();
            check = checkNull(string);
        }
        return check;
    }
}
