package ru.maven.jborn.dao.domain;

import org.junit.Before;
import org.junit.Test;
import ru.maven.jborn.dao.DaoFactory;
import ru.maven.jborn.models.Bill;
import ru.maven.jborn.models.Category;
import ru.maven.jborn.models.Transaction;
import ru.maven.jborn.models.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TransactionDaoTest {

    TransactionDao transactionDao;
    CategoryDao categoryDao;
    BillDao billDao;
    Bill bill;
    UserDao userDao;
    Category category;
    User user;
    Transaction transaction;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");
        System.setProperty("jdbcUsername", "sa");
        System.setProperty("jdbcPassword", "");

        transactionDao = DaoFactory.getTransactionDao();
        categoryDao = DaoFactory.getCategoryDao();
        userDao = DaoFactory.getUserDao();
        billDao = DaoFactory.getBillDao();

        category = new Category();
        category.setId(1);
        category.setCategoryName("CategoryName");

        if (categoryDao.findByAll().stream().noneMatch(cat -> cat.getCategoryName().equals("CategoryName"))) {
            categoryDao.insert(category);
        }

        user = new User();
        user.setLogin("testLogin");
        user.setFirstName("firstNameTest");
        user.setLastName("lastNameTest");
        user.setEmail("test@mail.ru");
        user.setPassword("123456");

        if (userDao.getUser(user.getLogin(), user.getPassword()).getLogin() == null) {
            userDao.insert(user);
        }

        User initUser = userDao.getUser(user.getLogin(), user.getPassword());

        bill = new Bill();
        bill.setNameAccount("BillName");
        bill.setUserId(initUser.getId());
        bill.setValues(BigDecimal.valueOf(1000));

        if (billDao.getListUserAccounts(initUser.getId()).size() == 0) {
            billDao.insert(bill);
        }

        transactionDao.findByAll().forEach(person -> transactionDao.delete(person.getId()));

        transaction = new Transaction();
        transaction.setUserId(initUser.getId());
        transaction.setNameAccount("BillName");
        transaction.setValues(BigDecimal.valueOf(777));
        transaction.setCategoryName("CategoryName");
        transaction.setDate(new Date());

    }

    @Test
    public void findById_Success() {
        Transaction tempTransaction = transactionDao.insert(transaction);

        Transaction result = transactionDao.findById(tempTransaction.getId());

        assertEquals(result.getNameAccount(), tempTransaction.getNameAccount());
        assertEquals(result.getCategoryName(), transaction.getCategoryName());
    }

    @Test
    public void findByAll_Success() {
        transactionDao.insert(transaction);

        List<Transaction> result = transactionDao.findByAll();

        assertEquals(1, result.size());
    }

    @Test
    public void findByAll_Empty() {
        List<Transaction> result = transactionDao.findByAll();

        assertTrue(result.isEmpty());
    }

    @Test
    public void insert_Success() {
        Transaction result = transactionDao.insert(transaction);

        assertEquals(result.getCategoryName(), transaction.getCategoryName());
        assertEquals(result.getNameAccount(), transaction.getNameAccount());
    }

    @Test
    public void delete_Success() {
        Transaction tempTransaction = transactionDao.insert(transaction);

        boolean result = transactionDao.delete(tempTransaction.getId());

        assertTrue(result);
    }

    @Test
    public void delete_NotFound() {
        boolean result = transactionDao.delete(999);

        assertFalse(result);
    }
}