package ru.maven.jborn.services;

import ru.maven.jborn.dao.domain.TransactionDao;
import ru.maven.jborn.mappers.TransactionMapper;
import ru.maven.jborn.models.Transaction;
import ru.maven.jborn.models.dto.BillDto;
import ru.maven.jborn.models.dto.TransactionDto;
import ru.maven.jborn.models.dto.UserDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {
    private final TransactionDao transactionDao;
    private final TransactionMapper transactionMapper;
    private final BillService billService;

    public TransactionService(TransactionDao transactionDao, TransactionMapper transactionMapper, BillService billService) {
        this.transactionDao = transactionDao;
        this.transactionMapper = transactionMapper;
        this.billService = billService;
    }

    public List<TransactionDto> getAllTransactionUser(UserDto user) {
        List<Transaction> transactionListAllUsers = transactionDao.findByAll();
        return transactionListAllUsers.stream().filter(x -> x.getUserId().equals(user.getId()))
                .map(transactionMapper::transactionToTransactionDto).collect(Collectors.toList());
    }

    public TransactionDto getTransactionUserById(UserDto userDto, Integer id) {
        Transaction tr = transactionDao.findById(id);
        return tr.getUserId() != null && tr.getUserId().equals(userDto.getId()) ?
                transactionMapper.transactionToTransactionDto(tr) : new TransactionDto();
    }

    public TransactionDto createTransaction(UserDto userDto, String nameAccount, BigDecimal values, String categoryName) {
        Transaction transaction = new Transaction();
        transaction.setDate(Date.from(Instant.now()));
        transaction.setNameAccount(nameAccount);
        transaction.setValues(values);
        transaction.setCategoryName(categoryName);
        transaction.setUserId(userDto.getId());
        return billService.updateBill(transaction).getId() == null ?
                new TransactionDto() : transactionMapper.transactionToTransactionDto(transactionDao.insert(transaction));
    }

    public int deleteTransactionUser(UserDto userDto, Integer id) {
        Transaction tr = transactionDao.findById(id);
        if (tr.getUserId() != null && tr.getUserId().equals(userDto.getId())) {
            if (transactionDao.delete(id)) {
                return 1;
            } else return 2;
        }
        return 0;
    }

    public List<TransactionDto> transactionsBetweenAccounts(UserDto userDto, String password, String sender,
                                                            String recipient, BigDecimal values) {
        List<BillDto> checkBillByUser = billService.getListUserAccounts(userDto, password);
        List<TransactionDto> result = new ArrayList<>();
        if (checkBillByUser.isEmpty()) {
            return new ArrayList<>();
        } else {
            long resultCheck = checkBillByUser.stream()
                    .filter(b -> b.getNameAccount().equals(sender) || b.getNameAccount().equals(recipient)).count();
            if (resultCheck == 2) {
                String categoryName = "Свой Перевод";
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(values)).negate();
                TransactionDto transactionDtoSender = createTransaction(userDto, sender, bigDecimal, categoryName);
                if (transactionDtoSender.getId() == null) {
                    return new ArrayList<>();
                } else {
                    result.add(createTransaction(userDto, recipient, values, categoryName));
                    result.add(transactionDtoSender);

                }
            } else {
                return new ArrayList<>();
            }
        }
        return result;
    }
}
