package ru.maven.jborn.services;

import ru.maven.jborn.dao.domain.BillDao;
import ru.maven.jborn.mappers.BillMapper;
import ru.maven.jborn.models.Bill;
import ru.maven.jborn.models.User;
import ru.maven.jborn.models.dto.BillDto;

public class BillService {
    private static Integer numberAcc = 10000;
    BillDao billDao = BillDao.getBillDao();
    BillMapper billMapper = new BillMapper();

    public BillDto createBill(User user) {
        Bill bill = new Bill();
        bill.setNumberAccounts(getNumberAccount());
        bill.setUserId(user.getId());
        bill.setValues(0);
        Bill resultBillDao = billDao.insert(bill);
        return billMapper.billToBillDto(resultBillDao);
    }

    private Integer getNumberAccount() {
        return numberAcc++;
    }
}
