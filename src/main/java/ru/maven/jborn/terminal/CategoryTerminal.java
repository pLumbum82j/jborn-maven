package ru.maven.jborn.terminal;

import ru.maven.jborn.models.dto.CategoryDto;
import ru.maven.jborn.services.CategoryService;

import java.util.Scanner;

public class CategoryTerminal {
    CategoryService categoryService = new CategoryService();
    Scanner scanner = new Scanner(System.in);

    String categoryName;

    public void createCategory() {
        while (true) {
            System.out.println();
            System.out.println("Что вы хотите сделать:");
            System.out.println("[1] Создать категорию");
            System.out.println("[-2] Посмотреть все категории");
            System.out.println("[-3] Найти категорию по id");
            System.out.println("[0] Выход из программы");
            switch (scanner.nextInt()) {
                case (1):
                    System.out.println("Вы выбрали = Создать категорию");
                    System.out.println("-----------------------------------");
                    System.out.println("Введите имя для категории");
                    Scanner scanner1 = new Scanner(System.in);
                    categoryName = checkNull(scanner1.nextLine());
                    CategoryDto categoryDto = categoryService.createCategory(categoryName);
                    if (categoryDto.getId() == null) {
                        System.out.println("Такая категория уже существует");
                    } else {
                        System.out.println("Категория создана: " + categoryDto);
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
