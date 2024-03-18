package ru.maven.jborn.terminal;

import ru.maven.jborn.models.dto.TransactionDto;
import ru.maven.jborn.models.dto.UserDto;
import ru.maven.jborn.services.BillService;
import ru.maven.jborn.services.CategoryService;
import ru.maven.jborn.services.ServiceFactory;
import ru.maven.jborn.services.TransactionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class TransactionTerminal {
    TransactionService transactionService = ServiceFactory.getTransactionService();
    BillService billService = ServiceFactory.getBillService();
    CategoryService categoryService = ServiceFactory.getCategoryService();
    Scanner scanner = new Scanner(System.in);

    public void transactionMenu(UserDto user, String password) {
        while (true) {
            System.out.println();
            System.out.println("Что вы хотите сделать:");
            System.out.println("[1] Посмотреть все транзакции");
            System.out.println("[2] Добавить транзакцию");
            System.out.println("[3] Показать транзакцию по id");
            System.out.println("[4] Удалить транзакцию");
            System.out.println("[5] Перевод средств между счетами");
            System.out.println("[0] Выйти из меню");
            switch (scanner.nextInt()) {
                case (1):
                    System.out.println("Вы выбрали = Посмотреть все транзакции");
                    System.out.println("-----------------------------------");
                    List<TransactionDto> resultList = transactionService.getAllTransactionUser(user);
                    if (resultList.isEmpty()) {
                        System.out.println("Транзакции отсутствуют");
                    } else {
                        System.out.println(resultList);
                    }
                    break;
                case (2):
                    System.out.println("Вы выбрали = Добавить транзакцию");
                    System.out.println("-----------------------------------");
                    System.out.println(billService.getBillAllUser(user, password));
                    System.out.println("Введите имя счёта");
                    Scanner scanner1 = new Scanner(System.in);
                    String nameAccount = scanner1.nextLine();
                    System.out.println("Введите значение (сумма или разность операции)");
                    BigDecimal values = new BigDecimal(String.valueOf(scanner.nextBigDecimal()));
                    System.out.println(categoryService.findByAllCategory());
                    System.out.println("Введите категорию");
                    Scanner scanner2 = new Scanner(System.in);
                    String categoryName = scanner2.nextLine();
                    TransactionDto transactionDto = transactionService.createTransaction(user, nameAccount, values, categoryName);
                    if (transactionDto.getId() == null) {
                        System.out.println("Операция не выполнена");
                    } else {
                        System.out.println(transactionDto);
                    }
                    break;
                case (3):
                    System.out.println("Вы выбрали = Показать транзакцию по id");
                    System.out.println("-----------------------------------");
                    System.out.println("Введите id транзакции");
                    TransactionDto resultTransactionDto = transactionService.getTransactionUserById(user, scanner.nextInt());
                    if (resultTransactionDto.getId() != null) {
                        System.out.println(resultTransactionDto);
                    } else {
                        System.out.println("такой транзакции нет у пользователя");
                    }
                    break;
                case (4):
                    System.out.println("Вы выбрали = Удалить транзакцию");
                    System.out.println("-----------------------------------");
                    System.out.println("Введите id транзакции");
                    int result = transactionService.deleteTransactionUser(user, scanner.nextInt());
                    if (result == 1) {
                        System.out.println("Транзакция удалена");
                    } else {
                        System.out.println("такой транзакции нет у пользователя");
                    }
                    break;
                case (5):
                    System.out.println("Перевод средств между счетами");
                    System.out.println("-----------------------------------");
                    System.out.println("Введите название счёта с которого списать средства:");
                    Scanner scanner3 = new Scanner(System.in);
                    String sender = scanner3.nextLine();
                    System.out.println("Введите название счёта на который перевести средства:");
                    Scanner scanner4 = new Scanner(System.in);
                    String recipient = scanner4.nextLine();
                    System.out.println("Введите значение (сумма или разность операции)");
                    BigDecimal values1 = new BigDecimal(String.valueOf(scanner.nextBigDecimal()));
                    List<TransactionDto> transactionDtoList = transactionService
                            .transactionsBetweenAccounts(user, password, sender, recipient, values1);
                    if (transactionDtoList.isEmpty()) {
                        System.out.println("Операция не выполнена");
                    } else {
                        System.out.println(transactionDtoList);
                    }
                    break;
                case (0):
                    return;
                default:
                    System.out.println("Такого варианта выбора нет");
            }
        }
    }
}
