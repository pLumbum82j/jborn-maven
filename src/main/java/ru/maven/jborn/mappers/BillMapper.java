package ru.maven.jborn.mappers;

import ru.maven.jborn.models.Bill;
import ru.maven.jborn.models.dto.BillDto;

public class BillMapper {

    public BillDto billToBillDto(Bill bill) {
        BillDto billDto = new BillDto();
        billDto.setId(bill.getId());
        billDto.setNameAccounts(bill.getNameAccount());
        billDto.setValues(bill.getValues());
        return billDto;
    }
}
