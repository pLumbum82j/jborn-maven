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
import ru.maven.jborn.models.User;
import ru.maven.jborn.models.dto.BillDto;
import ru.maven.jborn.models.dto.UserDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
    Bill bill;
    BillDto billDto;

    @Before
    public void setUp() {
        user = new User();
        userDto = new UserDto();
        bill = new Bill();
        billDto = new BillDto();
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
        bill.setValues(0);
        billDto.setId(1);
        billDto.setNameAccounts("Сбердебанк");
        billDto.setValues(0);
    }

    @Test
    public void createBill_Success() {
        Map<Integer, String> billZero = new HashMap<>();
        when(userDao.getUser(userDto.getLogin(), "1")).thenReturn(user);
        when(billDao.checkDuplicateInvoiceAndCount(bill)).thenReturn(billZero);
        when(billDao.insert(bill)).thenReturn(bill);
        when(billMapper.billToBillDto(bill)).thenReturn(billDto);

        BillDto resultBillDto = billService.createBill(userDto, "1", "Сбердебанк");

        assertEquals(resultBillDto, billDto);
    }

    @Test
    public void createBill_Duplicate() {
        Map<Integer, String> billDuplicateName = new HashMap<>();
        billDuplicateName.put(1, bill.getNameAccount());
        when(userDao.getUser(userDto.getLogin(), "1")).thenReturn(user);
        when(billDao.checkDuplicateInvoiceAndCount(bill)).thenReturn(billDuplicateName);
        when(billDao.insert(bill)).thenReturn(bill);
        when(billMapper.billToBillDto(bill)).thenReturn(billDto);

        BillDto resultBillDto = billService.createBill(userDto, "1", "Сбердебанк");

        assertEquals(resultBillDto.getNameAccounts(), "duplicate");
    }

    @Test
    public void createBill_MoreThanFive() {
        Map<Integer, String> billList = new HashMap<>();
        billList.put(1, "Счёт №1");
        billList.put(2, "Счёт №2");
        billList.put(3, "Счёт №3");
        billList.put(4, "Счёт №4");
        billList.put(5, "Счёт №5");
        when(userDao.getUser(userDto.getLogin(), "1")).thenReturn(user);
        when(billDao.checkDuplicateInvoiceAndCount(bill)).thenReturn(billList);
        when(billDao.insert(bill)).thenReturn(bill);
        when(billMapper.billToBillDto(bill)).thenReturn(billDto);

        BillDto resultBillDto = billService.createBill(userDto, "1", "Сбердебанк");

        assertNull(resultBillDto.getNameAccounts());
    }

    @Test
    public void getBillAllUser() {
        user.setId(1);
       // bill.setUserId(1);
        bill.setId(1);
        List<Bill> billList = new ArrayList<>();
        billList.add(bill);
        System.out.println(bill);
        when(userDao.getUser(userDto.getLogin(), "1")).thenReturn(user);
        when(billDao.findByAll()).thenReturn(billList);
        when(billMapper.billToBillDto(bill)).thenReturn(billDto);

        List<BillDto> resultList = billService.getBillAllUser(userDto, "1");

        assertEquals(resultList.size(), 1);


    }

    @Test
    public void updateBill() {
    }

    @Test
    public void removeBillUser() {
    }
}