package ru.maven.jborn.mappers;

import org.junit.Before;
import org.junit.Test;
import ru.maven.jborn.models.Transaction;
import ru.maven.jborn.models.dto.TransactionDto;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TransactionMapperTest {

    TransactionMapper transactionMapper;
    Transaction transaction;

    @Before
    public void setUp() {
        transactionMapper = new TransactionMapper();
        transaction = new Transaction();
    }

    @Test
    public void transactionToTransactionDto_Success() {
        transaction.setId(1);
        transaction.setDate(new Date());
        transaction.setNameAccount("NameBill");
        transaction.setValues(new BigDecimal(300));
        transaction.setCategoryName("CategoryName");
        transaction.setUserId(1);

        TransactionDto result = transactionMapper.transactionToTransactionDto(transaction);

        assertEquals(result.getId(), transaction.getId());
        assertEquals(result.getDate(), transaction.getDate());
        assertEquals(result.getNameAccount(), transaction.getNameAccount());
        assertEquals(result.getCategoryName(), transaction.getCategoryName());
        assertEquals(result.getUserId(), transaction.getUserId());
    }

    @Test
    public void transactionToTransactionDto_FieldsNull() {
        TransactionDto result = transactionMapper.transactionToTransactionDto(null);

        assertNull(result.getId());
        assertNull(result.getDate());
        assertNull(result.getNameAccount());
        assertNull(result.getCategoryName());
        assertNull(result.getUserId());
    }
}