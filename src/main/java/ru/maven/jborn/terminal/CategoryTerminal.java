package ru.maven.jborn.terminal;

import ru.maven.jborn.models.dto.CategoryDto;
import ru.maven.jborn.services.CategoryService;
import ru.maven.jborn.services.ServiceFactory;

import java.util.List;
import java.util.Scanner;

public class CategoryTerminal {
    CategoryService categoryService = ServiceFactory.getCategoryService();
    Scanner scanner = new Scanner(System.in);

    public void categoryMenu() {
        while (true) {
            System.out.println();
            System.out.println("Что вы хотите сделать:");
            System.out.println("[1] Создать категорию");
            System.out.println("[2] Посмотреть все категории");
            System.out.println("[3] Найти категорию по id");
            System.out.println("[4] Удалить категорию по id");
            System.out.println("[0] Выйти из меню категорий");
            switch (scanner.nextInt()) {
                case (1):
                    System.out.println("Вы выбрали = Создать категорию");
                    System.out.println("-----------------------------------");
                    System.out.println("Введите имя для категории");
                    Scanner scanner1 = new Scanner(System.in);
                    CategoryDto categoryDto = categoryService.createCategory(checkNull(scanner1.nextLine()));
                    if (categoryDto.getId() == null) {
                        System.out.println("Такая категория уже существует");
                    } else {
                        System.out.println("Категория создана: " + categoryDto);
                    }
                    break;
                case (2):
                    System.out.println("Вы выбрали = Посмотреть все категории");
                    System.out.println("-----------------------------------");
                    List<CategoryDto> resultListCategoryDto = categoryService.findByAllCategory();
                    if (resultListCategoryDto.isEmpty()) {
                        System.out.println("Категории в базе данных отсутсвуют");
                    } else {
                        System.out.println(resultListCategoryDto);
                    }
                    break;
                case (3):
                    System.out.println("Вы выбрали = Найти категорию по id");
                    System.out.println("-----------------------------------");
                    System.out.println("ВВедите id категории");
                    CategoryDto resutCategoryDto = categoryService.findCategoryById(scanner.nextInt());
                    if (resutCategoryDto.getId() == null) {
                        System.out.println("Данная категория отсутсвует в базе данных");
                    } else {
                        System.out.println(resutCategoryDto);
                    }
                    break;
                case (4):
                    System.out.println("Вы выбрали = Удалить категорию по id");
                    System.out.println("-----------------------------------");
                    boolean result = categoryService.removeCategoryById(scanner.nextInt());
                    if (result) {
                        System.out.println("Категория удалена");
                    } else {
                        System.out.println("Категория отсутвует в базе данных");
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
