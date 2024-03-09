package ru.maven.jborn.services;

import ru.maven.jborn.dao.domain.TransactionDao;
import ru.maven.jborn.mappers.TransactionMapper;
import ru.maven.jborn.models.Transaction;
import ru.maven.jborn.models.dto.BillDto;
import ru.maven.jborn.models.dto.TransactionDto;
import ru.maven.jborn.models.dto.UserDto;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {
    TransactionDao transactionDao = TransactionDao.getTransactionDao();
    TransactionMapper transactionMapper = new TransactionMapper();
    BillService billService = new BillService();

    public List<TransactionDto> getAllTransactionUser(UserDto user) {
        List<Transaction> transactionListAllUsers = transactionDao.findByAll();
        return transactionListAllUsers.stream().filter(x -> x.getUserId().equals(user.getId()))
                .map(o -> transactionMapper.transactionToTransactionDto(o)).collect(Collectors.toList());
    }

    public TransactionDto getTransactionUserById(UserDto userDto, Integer id) {
        Transaction tr = transactionDao.findById(id);
        if (tr.getUserId() != null && tr.getUserId().equals(userDto.getId())) {
            return transactionMapper.transactionToTransactionDto(tr);
        } else {
            return new TransactionDto();
        }
    }

    public TransactionDto createTransaction(UserDto userDto, String nameAccount, Integer values, String categoryName) {
        Transaction transaction = new Transaction();
        transaction.setDate(Date.from(Instant.now()));
        transaction.setNameAccount(nameAccount);
        transaction.setValues(values);
        transaction.setCategoryName(categoryName);
        transaction.setUserId(userDto.getId());
        BillDto billDto = billService.updateBill(transaction);
        if (billDto.getId() == null) {
            return new TransactionDto();
        } else {
            return transactionMapper.transactionToTransactionDto(transactionDao.insert(transaction));
        }
    }

    public int deleteTransactionUser(UserDto userDto, Integer id) {
        Transaction tr = transactionDao.findById(id);
        if (tr.getUserId() != null && tr.getUserId().equals(userDto.getId())) {
            boolean resultDao = transactionDao.delete(id);
            if (resultDao) {
                return 1;
            } else return 2;
        }
        return 0;
    }
}
