package ru.maven.jborn.services;

import ru.maven.jborn.dao.domain.BillDao;
import ru.maven.jborn.dao.domain.UserDao;
import ru.maven.jborn.mappers.BillMapper;
import ru.maven.jborn.models.Bill;
import ru.maven.jborn.models.Transaction;
import ru.maven.jborn.models.User;
import ru.maven.jborn.models.dto.BillDto;
import ru.maven.jborn.models.dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BillService {
    private final BillDao billDao;
    private final BillMapper billMapper;
    private final UserDao userDao;

    public BillService(BillDao billDao, BillMapper billMapper, UserDao userDao) {
        this.billDao = billDao;
        this.billMapper = billMapper;
        this.userDao = userDao;
    }

    public BillDto createBill(UserDto user, String password, String nameAccount) {
        Bill bill = new Bill();
        User tempUser = userDao.getUser(user.getLogin(), password);
        bill.setNameAccount(nameAccount);
        bill.setUserId(tempUser.getId());
        bill.setValues(0);
        int checkDuplicate = checkDuplicateInvoiceAndCount(bill);
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

    public List<BillDto> getBillAllUser(UserDto user, String password) {
        List<BillDto> result = new ArrayList<>();
        User tempUser = userDao.getUser(user.getLogin(), password);
        List<Bill> tempListBillAll;
        tempListBillAll = billDao.findByAll();
        List<Bill> resultList = tempListBillAll.stream()
                .filter(x -> x.getUserId().equals(tempUser.getId()))
                .collect(Collectors.toList());
        return resultList.isEmpty() ? result : resultList.stream()
                .map(billMapper::billToBillDto)
                .collect(Collectors.toList());
    }

    public BillDto updateBill(Transaction transaction) {
        Bill bill = new Bill();
        bill.setNameAccount(transaction.getNameAccount());
        bill.setUserId(transaction.getUserId());
        bill.setId(billDao.getBillId(bill));
        Integer balance = billDao.findById(bill.getId()).getValues();
        if ((balance + transaction.getValues()) > 0) {
            bill.setValues(balance + transaction.getValues());
            return billMapper.billToBillDto(billDao.update(bill));
        } else {
            return new BillDto();
        }

    }

    public boolean removeBillUser(UserDto user, String password, String nameBill) {
        User tempUser = userDao.getUser(user.getLogin(), password);
        List<Bill> resultListNameBillUser = billDao.findByAll().stream()
                .filter(o -> o.getUserId().equals(tempUser.getId()))
                .filter(b -> b.getNameAccount().equals(nameBill)).collect(Collectors.toList());
        return billDao.delete(resultListNameBillUser.get(0).getId());
    }

    private Integer checkDuplicateInvoiceAndCount(Bill bill) {
        Map<Integer, String> tempAccountUser = billDao.checkDuplicateInvoiceAndCount(bill);
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
