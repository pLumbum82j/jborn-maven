package ru.maven.jborn.mappers;

import ru.maven.jborn.models.Transaction;
import ru.maven.jborn.models.dto.TransactionDto;

public class TransactionMapper {

    public TransactionDto transactionToTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setDate(transaction.getDate());
        transactionDto.setNameAccount(transaction.getNameAccount());
        transactionDto.setValues(transaction.getValues());
        transactionDto.setSpendingCategoryName(transaction.getCategoryName());
        transactionDto.setUserId(transaction.getUserId());
        return transactionDto;
    }
}
