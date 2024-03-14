package ru.maven.jborn.mappers;

import org.junit.Before;
import org.junit.Test;
import ru.maven.jborn.models.Bill;
import ru.maven.jborn.models.dto.BillDto;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BillMapperTest {
    BillMapper billMapper;
    Bill bill;

    @Before
    public void setUp() {
        billMapper = new BillMapper();
        bill = new Bill();
    }

    @Test
    public void billToBillDto_Success() {
        bill.setId(1);
        bill.setNameAccount("NameBill");
        bill.setUserId(1);
        bill.setValues(new BigDecimal(100));

        BillDto result = billMapper.billToBillDto(bill);

        assertEquals(result.getId(), bill.getId());
        assertEquals(result.getNameAccount(), bill.getNameAccount());
        assertEquals(result.getValues(), bill.getValues());
    }

    @Test
    public void billToBillDto_FieldsNull() {
        BillDto result = billMapper.billToBillDto(null);

        assertNull(result.getId());
        assertNull(result.getNameAccount());
        assertNull(result.getValues());

    }
}