package ru.maven.jborn.dao;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.maven.jborn.dao.domain.BillDao;
import ru.maven.jborn.dao.domain.CategoryDao;
import ru.maven.jborn.dao.domain.TransactionDao;
import ru.maven.jborn.dao.domain.UserDao;

import javax.sql.DataSource;
import java.sql.SQLException;


public class DaoFactory {
    private static DataSource dataSource;
    public static BillDao billDao;
    private static CategoryDao categoryDao;
    private static UserDao userDao;
    private static TransactionDao transactionDao;

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDao(DaoFactory.getDataSource());
        }
        return userDao;
    }

    public static BillDao getBillDao() {
        if (billDao == null) {
            billDao = new BillDao(DaoFactory.getDataSource());
        }
        return billDao;
    }

    public static CategoryDao getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryDao(DaoFactory.getDataSource());
        }
        return categoryDao;
    }

    public static TransactionDao getTransactionDao() {
        if (transactionDao == null) {
            transactionDao = new TransactionDao(DaoFactory.getCategoryDao(), DaoFactory.getDataSource());
        }
        return transactionDao;
    }


    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl("jdbc:postgresql://localhost:5432/jborn_finance");
            ds.setUsername("jborn");
            ds.setPassword("jborn");
            dataSource = ds;
            initDataBase();
        }
        return dataSource;
    }

    private static void initDataBase() {
        try {
            DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
            Liquibase liquibase = new Liquibase(
                    "liquibase.xml",
                    new ClassLoaderResourceAccessor(),
                    database
            );
            liquibase.update(new Contexts());
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
}
