package ru.maven.jborn.mappers;

import ru.maven.jborn.models.Transaction;
import ru.maven.jborn.models.dto.TransactionDto;

public class TransactionMapper {

    public TransactionDto transactionToTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        if (transaction == null) {
            return transactionDto;
        }
        transactionDto.setId(transaction.getId());
        transactionDto.setDate(transaction.getDate());
        transactionDto.setNameAccount(transaction.getNameAccount());
        transactionDto.setValues(transaction.getValues());
        transactionDto.setCategoryName(transaction.getCategoryName());
        transactionDto.setUserId(transaction.getUserId());
        return transactionDto;
    }
}
