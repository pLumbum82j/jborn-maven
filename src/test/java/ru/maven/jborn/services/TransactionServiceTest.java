package ru.maven.jborn.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.maven.jborn.dao.domain.TransactionDao;
import ru.maven.jborn.mappers.TransactionMapper;
import ru.maven.jborn.models.Transaction;
import ru.maven.jborn.models.dto.BillDto;
import ru.maven.jborn.models.dto.TransactionDto;
import ru.maven.jborn.models.dto.UserDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    @InjectMocks
    TransactionService transactionService;
    @Mock
    TransactionDao transactionDao;
    @Mock
    TransactionMapper transactionMapper;
    @Mock
    BillService billService;

    Transaction transaction;
    TransactionDto transactionDto;
    UserDto userDto;
    BillDto billDto;
    Integer id = 1;

    @Before
    public void setUp() {
        userDto = new UserDto();
        userDto.setId(id);
        transaction = new Transaction();
        billDto = new BillDto();
        BigDecimal values = new BigDecimal(100);
        Date date = new Date();
        String nameBill = "nameBill";
        String nameCategory = "Продукты";
        transaction.setId(id);
        transaction.setUserId(userDto.getId());
        transaction.setNameAccount(nameBill);
        transaction.setValues(values);
        transaction.setDate(date);
        transaction.setCategoryName(nameCategory);
        transactionDto = new TransactionDto();
        transactionDto.setId(id);
        transactionDto.setUserId(userDto.getId());
        transactionDto.setNameAccount(nameBill);
        transactionDto.setValues(values);
        transactionDto.setDate(date);
        transactionDto.setCategoryName(nameCategory);
        billDto.setId(id);
        billDto.setNameAccounts(nameBill);
        billDto.setValues(values);
    }

    @Test
    public void getAllTransactionUser_Success() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        when(transactionDao.findByAll()).thenReturn(transactionList);
        when(transactionMapper.transactionToTransactionDto(transaction)).thenReturn(transactionDto);

        List<TransactionDto> result = transactionService.getAllTransactionUser(userDto);

        assertEquals(result.get(0).getId(), transactionDto.getId());
        assertEquals(result.get(0).getUserId(), transactionDto.getUserId());
    }

    @Test
    public void getAllTransactionUser_isEmpty() {
        List<Transaction> transactionList = new ArrayList<>();
        when(transactionDao.findByAll()).thenReturn(transactionList);
        when(transactionMapper.transactionToTransactionDto(null)).thenReturn(new TransactionDto());

        List<TransactionDto> result = transactionService.getAllTransactionUser(userDto);

        assertTrue(result.isEmpty());
    }

    @Test
    public void getTransactionUserById_Success() {
        when(transactionDao.findById(id)).thenReturn(transaction);
        when(transactionMapper.transactionToTransactionDto(transaction)).thenReturn(transactionDto);

        TransactionDto result = transactionService.getTransactionUserById(userDto, id);

        assertEquals(result.getUserId(), userDto.getId());
        assertEquals(result.getNameAccount(), transactionDto.getNameAccount());
    }

    @Test
    public void getTransactionUserById_NotFound() {
        when(transactionDao.findById(id)).thenReturn(new Transaction());

        TransactionDto result = transactionService.getTransactionUserById(userDto, id);

        assertNull(result.getId());
        assertNull(result.getUserId());
        assertNull(result.getNameAccount());
    }

    @Test
    public void createTransaction_Success() {
        when(billService.updateBill(anyObject())).thenReturn(billDto);
        when(transactionDao.insert(anyObject())).thenReturn(transaction);
        when(transactionMapper.transactionToTransactionDto(transaction)).thenReturn(transactionDto);

        TransactionDto result = transactionService
                .createTransaction(userDto, transaction.getNameAccount(),
                        transaction.getValues(), transaction.getCategoryName());

        assertEquals(result.getNameAccount(), transactionDto.getNameAccount());
    }

    @Test
    public void createTransaction_NotCreat() {
        when(billService.updateBill(anyObject())).thenReturn(new BillDto());

        TransactionDto result = transactionService
                .createTransaction(userDto, transaction.getNameAccount(),
                        transaction.getValues(), transaction.getCategoryName());

        assertNull(result.getId());
        assertNull(result.getNameAccount());
    }

    @Test
    public void deleteTransactionUser_Success() {
        when(transactionDao.findById(id)).thenReturn(transaction);
        when(transactionDao.delete(id)).thenReturn(true);

        int result = transactionService.deleteTransactionUser(userDto, id);

        assertEquals(result, 1);
    }

    @Test
    public void deleteTransactionUser_NotRemove() {
        when(transactionDao.findById(id)).thenReturn(transaction);
        when(transactionDao.delete(id)).thenReturn(false);

        int result = transactionService.deleteTransactionUser(userDto, id);

        assertEquals(result, 2);
    }

    @Test
    public void deleteTransactionUser_NotFound() {
        when(transactionDao.findById(id)).thenReturn(new Transaction());

        int result = transactionService.deleteTransactionUser(userDto, id);

        assertEquals(result, 0);
    }

    @Test
    public void transactionsBetweenAccounts_Success() {
        List<BillDto> billDtoList = new ArrayList<>();
        BillDto secondBill = new BillDto();
        TransactionDto transactionDto2 = new TransactionDto();
        transactionDto2.setId(2);
        transactionDto2.setUserId(userDto.getId());
        transactionDto2.setNameAccount("secondNameBill");
        transactionDto2.setValues(BigDecimal.valueOf(10));
        transactionDto2.setDate(transactionDto.getDate());
        transactionDto2.setCategoryName(transactionDto.getCategoryName());
        secondBill.setId(2);
        secondBill.setNameAccounts("secondNameBill");
        secondBill.setValues(BigDecimal.valueOf(100));
        billDtoList.add(secondBill);
        billDtoList.add(billDto);
        when(billService.getListUserAccounts(anyObject(), anyString())).thenReturn(billDtoList);
        when(billService.updateBill(anyObject())).thenReturn(billDto);
        when(transactionService.createTransaction(userDto, "nameBill", BigDecimal.valueOf(100), "Свой Перевод")).thenReturn(transactionDto);
        when(billService.updateBill(anyObject())).thenReturn(secondBill);
        when(transactionService.createTransaction(userDto, "secondNameBill", BigDecimal.valueOf(10), "Свой Перевод")).thenReturn(transactionDto2);

        List<TransactionDto> result = transactionService.transactionsBetweenAccounts(userDto, "1", "nameBill", "secondNameBill", BigDecimal.valueOf(10));

        assertEquals(2, result.size());
        assertEquals(result.get(0).getNameAccount(), transactionDto2.getNameAccount());
     //  assertEquals(result.get(1).getNameAccount(), transactionDto.getNameAccount());
    }

    @Test
    public void transactionsBetweenAccounts_BillNotFoundUser() {
        List<BillDto> billDtoList = new ArrayList<>();
        when(billService.getListUserAccounts(anyObject(), anyString())).thenReturn(billDtoList);

        List<TransactionDto> result = transactionService.transactionsBetweenAccounts(userDto, "1", "nameBill", "secondNameBill", BigDecimal.valueOf(10));

        assertTrue(result.isEmpty());
    }

    @Test
    public void transactionsBetweenAccounts_OnlyBillUser() {
        List<BillDto> billDtoList = new ArrayList<>();
        billDtoList.add(billDto);
        when(billService.getListUserAccounts(anyObject(), anyString())).thenReturn(billDtoList);

        List<TransactionDto> result = transactionService.transactionsBetweenAccounts(userDto, "1", "nameBill", "secondNameBill", BigDecimal.valueOf(10));

        assertTrue(result.isEmpty());
    }
}