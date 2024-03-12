package ru.maven.jborn.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.maven.jborn.dao.domain.BillDao;
import ru.maven.jborn.dao.domain.UserDao;
import ru.maven.jborn.mappers.BillMapper;
import ru.maven.jborn.models.Bill;
import ru.maven.jborn.models.Transaction;
import ru.maven.jborn.models.User;
import ru.maven.jborn.models.dto.BillDto;
import ru.maven.jborn.models.dto.UserDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BillServiceTest {
    @InjectMocks
    BillService billService;
    @Mock
    BillDao billDao;
    @Mock
    BillMapper billMapper;
    @Mock
    UserDao userDao;
    User user;
    UserDto userDto;
    BigDecimal value;
    Bill bill;
    BillDto billDto;
    List<Bill> billList;
    Map<Integer, String> billCheckDuplicate;

    @Before
    public void setUp() {
        user = new User();
        userDto = new UserDto();
        bill = new Bill();
        billDto = new BillDto();
        billList = new ArrayList<>();
        billCheckDuplicate = new HashMap<>();
        value = new BigDecimal(0);
        user.setId(1);
        user.setFirstName("Iliya");
        user.setLastName("Smirnov");
        user.setLogin("plumbum1");
        user.setEmail("pb8d2@mail.ru");
        user.setPassword("c4ca4238a0b923820dcc509a6f75849b");
        userDto.setId(1);
        userDto.setFirstName("Iliya");
        userDto.setLastName("Smirnov");
        userDto.setLogin("plumbum1");
        userDto.setEmail("pb8d2@mail.ru");
        bill.setUserId(user.getId());
        bill.setNameAccount("Сбердебанк");
        bill.setValues(value);
        billDto.setId(1);
        billDto.setNameAccounts("Сбердебанк");
        billDto.setValues(value);
    }

    @Test
    public void createBill_Success() {
        when(userDao.getUser(userDto.getLogin(), "1")).thenReturn(user);
        when(billDao.checkDuplicateInvoiceAndCount(bill)).thenReturn(billCheckDuplicate);
        when(billDao.insert(bill)).thenReturn(bill);
        when(billMapper.billToBillDto(bill)).thenReturn(billDto);

        BillDto resultBillDto = billService.createBill(userDto, "1", "Сбердебанк");

        assertEquals(resultBillDto, billDto);
    }

    @Test
    public void createBill_Duplicate() {
        billCheckDuplicate.put(1, bill.getNameAccount());
        when(userDao.getUser(userDto.getLogin(), "1")).thenReturn(user);
        when(billDao.checkDuplicateInvoiceAndCount(bill)).thenReturn(billCheckDuplicate);
        when(billDao.insert(bill)).thenReturn(bill);
        when(billMapper.billToBillDto(bill)).thenReturn(billDto);

        BillDto resultBillDto = billService.createBill(userDto, "1", "Сбердебанк");

        assertEquals(resultBillDto.getNameAccounts(), "duplicate");
    }

    @Test
    public void createBill_MoreThanFive() {
        billCheckDuplicate.put(1, "Счёт №1");
        billCheckDuplicate.put(2, "Счёт №2");
        billCheckDuplicate.put(3, "Счёт №3");
        billCheckDuplicate.put(4, "Счёт №4");
        billCheckDuplicate.put(5, "Счёт №5");
        when(userDao.getUser(userDto.getLogin(), "1")).thenReturn(user);
        when(billDao.checkDuplicateInvoiceAndCount(bill)).thenReturn(billCheckDuplicate);
        when(billDao.insert(bill)).thenReturn(bill);
        when(billMapper.billToBillDto(bill)).thenReturn(billDto);

        BillDto resultBillDto = billService.createBill(userDto, "1", "Сбердебанк");

        assertNull(resultBillDto.getNameAccounts());
    }

    @Test
    public void getBillAllUser_Success() {
        billList.add(bill);
        when(userDao.getUser(userDto.getLogin(), "1")).thenReturn(user);
        when(billDao.findByAll()).thenReturn(billList);
        when(billMapper.billToBillDto(bill)).thenReturn(billDto);

        List<BillDto> resultList = billService.getBillAllUser(userDto, "1");

        assertEquals(resultList.size(), 1);
    }

    @Test
    public void updateBill_SumSuccess() {
        bill.setId(1);
        billDto.setValues(new BigDecimal(300));
        Transaction transaction = new Transaction();
        transaction.setNameAccount("Сбердебанк");
        transaction.setUserId(user.getId());
        transaction.setValues(billDto.getValues());
        when(billDao.getBillId(anyObject())).thenReturn(bill.getId());
        when(billDao.findById(bill.getId())).thenReturn(bill);
        when(billDao.update(anyObject())).thenReturn(bill);
        when(billMapper.billToBillDto(bill)).thenReturn(billDto);

        BillDto result = billService.updateBill(transaction);
        assertEquals(result.getValues(), billDto.getValues());
    }

    @Test
    public void updateBill_ValueGreaterThanBalance() {
        bill.setId(1);
        billDto.setValues(new BigDecimal(-300));
        Transaction transaction = new Transaction();
        transaction.setNameAccount("Сбердебанк");
        transaction.setUserId(user.getId());
        transaction.setValues(billDto.getValues());
        when(billDao.getBillId(anyObject())).thenReturn(bill.getId());
        when(billDao.findById(bill.getId())).thenReturn(bill);
        when(billDao.update(anyObject())).thenReturn(bill);
        when(billMapper.billToBillDto(bill)).thenReturn(billDto);

        BillDto result = billService.updateBill(transaction);
        assertNull(result.getValues());
    }

    @Test
    public void removeBillUser_Success() {
        billList.add(bill);
        when(userDao.getUser(userDto.getLogin(), "1")).thenReturn(user);
        when(billDao.findByAll()).thenReturn(billList);
        when(billDao.delete(billList.get(0).getId())).thenReturn(true);

        boolean result = billService.removeBillUser(userDto, "1", "Сбердебанк");

        assertTrue(result);
    }
}