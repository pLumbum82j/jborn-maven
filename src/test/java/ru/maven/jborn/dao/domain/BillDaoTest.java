package ru.maven.jborn.dao.domain;

import org.junit.Before;
import org.junit.Test;
import ru.maven.jborn.dao.DaoFactory;
import ru.maven.jborn.models.Bill;
import ru.maven.jborn.models.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BillDaoTest {

    BillDao billDao;
    Bill bill;
    UserDao userDao;
    User user;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");
        System.setProperty("jdbcUsername", "sa");
        System.setProperty("jdbcPassword", "");

        billDao = DaoFactory.getBillDao();
        userDao = DaoFactory.getUserDao();

        user = new User();
        user.setLogin("testLogin");
        user.setFirstName("firstNameTest");
        user.setLastName("lastNameTest");
        user.setEmail("test@mail.ru");
        user.setPassword("123456");

        if (userDao.getUser(user.getLogin(), user.getPassword()).getLogin() == null) {
            userDao.insert(user);
        }

        billDao.findByAll().forEach(billElement -> billDao.delete(billElement.getId()));

        bill = new Bill();
        bill.setNameAccount("BillName");
        bill.setUserId(1);
        bill.setValues(BigDecimal.valueOf(1000));


    }

    @Test
    public void insert_Success() {
        Bill result = billDao.insert(bill);

        assertEquals(result.getNameAccount(), bill.getNameAccount());
        assertEquals(result.getUserId(), bill.getUserId());
    }

    @Test
    public void findById_Success() {
        Bill tempBill = billDao.insert(bill);

        Bill result = billDao.findById(tempBill.getId());

        assertEquals(bill.getNameAccount(), result.getNameAccount());

    }

    @Test
    public void findByAll_Success() {
        billDao.insert(bill);

        List<Bill> result = billDao.findByAll();

        assertEquals(1, result.size());
        assertEquals(result.get(0).getNameAccount(), bill.getNameAccount());
    }

    @Test
    public void findByAll_Empty() {
        List<Bill> result = billDao.findByAll();

        assertTrue(result.isEmpty());
    }

    @Test
    public void update_Success() {
        Bill tempBill = billDao.insert(bill);
        Bill updateBill = new Bill();
        updateBill.setId(tempBill.getId());
        updateBill.setUserId(tempBill.getUserId());
        updateBill.setNameAccount(tempBill.getNameAccount());
        updateBill.setValues(BigDecimal.valueOf(999));

        Bill result = billDao.update(updateBill);

        assertEquals(updateBill.getValues(), result.getValues());
        assertEquals(updateBill.getNameAccount(), result.getNameAccount());
    }

    @Test
    public void delete_Success() {
        Bill tempBill = billDao.insert(bill);

        boolean result = billDao.delete(tempBill.getId());

        assertTrue(result);
    }

    @Test
    public void checkDuplicateInvoiceAndCount() {
        billDao.insert(bill);

        Map<Integer, String> result = billDao.checkDuplicateInvoiceAndCount(bill);

        assertEquals(result.size(), 1);
    }

    @Test
    public void getBillId() {
        Bill tempBill = billDao.insert(bill);

        Integer result = billDao.getBillId(bill);

        assertEquals(result, tempBill.getId());
    }

    @Test
    public void getListUserAccounts() {
        billDao.insert(bill);

        List<Bill> result = billDao.getListUserAccounts(1);

        assertEquals(result.size(), 1);
    }
}