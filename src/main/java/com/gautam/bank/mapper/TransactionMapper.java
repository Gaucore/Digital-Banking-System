package com.gautam.bank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gautam.bank.dto.response.transaction.TransactionResponse;
import com.gautam.bank.entity.transaction.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(
            target = "fromAccountNumber",
            source = "fromAccount.accountNumber"
    )
    @Mapping(
            target = "toAccountNumber",
            source = "toAccount.accountNumber"
    )
    TransactionResponse toResponse(Transaction transaction);

}
