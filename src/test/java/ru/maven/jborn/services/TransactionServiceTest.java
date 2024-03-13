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
import ru.maven.jborn.models.dto.TransactionDto;
import ru.maven.jborn.models.dto.UserDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    @Before
    public void setUp() {
        userDto = new UserDto();
        userDto.setId(1);
        transaction = new Transaction();
        BigDecimal values = new BigDecimal(100);
        Date date = new Date();
        String nameBill = "nameBill";
        transaction.setId(1);
        transaction.setUserId(userDto.getId());
        transaction.setNameAccount(nameBill);
        transaction.setValues(values);
        transaction.setDate(date);
        transactionDto = new TransactionDto();
        transactionDto.setId(1);
        transactionDto.setUserId(userDto.getId());
        transactionDto.setNameAccount(nameBill);
        transactionDto.setValues(values);
        transactionDto.setDate(date);
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
    public void getTransactionUserById() {
    }

    @Test
    public void createTransaction() {
    }

    @Test
    public void deleteTransactionUser() {
    }
}