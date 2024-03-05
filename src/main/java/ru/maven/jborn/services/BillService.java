package ru.maven.jborn.services;

import ru.maven.jborn.dao.domain.BillDao;
import ru.maven.jborn.mappers.BillMapper;
import ru.maven.jborn.models.Bill;
import ru.maven.jborn.models.User;
import ru.maven.jborn.models.dto.BillDto;

import java.util.HashMap;
import java.util.Map;

public class BillService {
    BillDao billDao = BillDao.getBillDao();
    BillMapper billMapper = new BillMapper();

    public BillDto createBill(User user, String nameAccount) {
        Bill bill = new Bill();
        bill.setNameAccount(nameAccount);
        bill.setUserId(user.getId());
        bill.setValues(0);
        Integer checkDuplicate = checkDuplicateInvoiceAndCount(bill);
        if (checkDuplicate == 1) {
            return new BillDto();
        } else if (checkDuplicate == 2) {
            BillDto duplicateDto = new BillDto();
            duplicateDto.setNameAccounts("duplicate");
            return duplicateDto;
        } else {
            Bill resultBillDao = billDao.insert(bill);
            return billMapper.billToBillDto(resultBillDao);
        }
    }

    private Integer checkDuplicateInvoiceAndCount(Bill bill) {
        Map<Integer, String> tempAccountUser;
        tempAccountUser = BillDao.billDao.checkDuplicateInvoiceAndCount(bill);
        boolean name = tempAccountUser.containsValue(bill.getNameAccount());
        int size = tempAccountUser.size();
        if (size >= 5) {
            return 1;
        } else if (name) {
            return 2;
        } else {
            return 3;
        }
    }
}
