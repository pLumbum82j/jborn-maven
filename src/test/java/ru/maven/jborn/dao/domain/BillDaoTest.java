package ru.maven.jborn.dao.domain;

import org.junit.Before;
import org.junit.Test;
import ru.maven.jborn.dao.DaoFactory;
import ru.maven.jborn.models.Bill;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class BillDaoTest {

    BillDao billDao;
    Bill bill;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");
        System.setProperty("jdbcUsername", "sa");
        System.setProperty("jdbcPassword", "");

        billDao = DaoFactory.getBillDao();

        bill = new Bill();
        bill.setNameAccount("BillName");
        bill.setUserId(1);
        bill.setValues(BigDecimal.valueOf(1000));
    }

    @Test
    public void insert() {
        Bill result = billDao.insert(bill);
        assertEquals(result.getId(), bill.getId());
    }

    @Test
    public void findById() {
        billDao.insert(bill);
        Bill result = billDao.findById(1);

        assertEquals(1, Optional.ofNullable(result.getId()));

    }

    @Test
    public void findByAll() {
    }



    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void checkDuplicateInvoiceAndCount() {
    }

    @Test
    public void getBillId() {
    }

    @Test
    public void getListUserAccounts() {
    }



    @Test
    public void testInsert() {
    }
}