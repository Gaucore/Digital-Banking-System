package com.gautam.bank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gautam.bank.dto.request.account.AccountRequest;
import com.gautam.bank.dto.response.account.AccountResponse;
import com.gautam.bank.entity.account.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toEntity(AccountRequest request);

    @Mapping(target = "customerCode",
            source = "customer.customerCode")
    @Mapping(
            target = "customerName",
            expression = "java(account.getCustomer().getFirstName() + \" \" + account.getCustomer().getLastName())"
    )
    @Mapping(
            target = "status",
            source = "accountStatus"
    )
    AccountResponse toResponse(Account account);

}