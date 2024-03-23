package ru.maven.jborn.services;

import ru.maven.jborn.dao.DaoFactory;
import ru.maven.jborn.mappers.BillMapper;
import ru.maven.jborn.mappers.CategoryMapper;
import ru.maven.jborn.mappers.TransactionMapper;
import ru.maven.jborn.mappers.UserMapper;

public class ServiceFactory {
    private static BillService billService;
    private static UserService userService;
    private static CategoryService categoryService;
    private static TransactionService transactionService;


    public static BillService getBillService() {
        if (billService == null) {
            billService = new BillService(DaoFactory.getBillDao(), new BillMapper(), DaoFactory.getUserDao());
        }
        return billService;
    }

    public static UserService getUserService() {
        if (userService == null) {
            userService = new UserService(new UserMapper(), DaoFactory.getUserDao());
        }
        return userService;
    }

    public static CategoryService getCategoryService() {
        if (categoryService == null) {
            categoryService = new CategoryService(new CategoryMapper(), DaoFactory.getCategoryDao());
        }
        return categoryService;
    }

    public static TransactionService getTransactionService() {
        if (transactionService == null) {
            transactionService = new TransactionService(DaoFactory.getTransactionDao(), new TransactionMapper(), getBillService());
        }
        return transactionService;
    }

}
