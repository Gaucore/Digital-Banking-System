package com.gautam.bank.service.impl;

import org.springframework.stereotype.Service;

import com.gautam.bank.entity.sequence.CodeSequence;
import com.gautam.bank.exception.ResourceNotFoundException;
import com.gautam.bank.repository.CodeSequenceRepository;
import com.gautam.bank.service.CodeSequenceService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CodeSequenceServiceImpl implements CodeSequenceService {

    private final CodeSequenceRepository codeSequenceRepository;

    private Long getNextValue(String sequenceName) {

        CodeSequence codeSequence = codeSequenceRepository
                .findBySequenceName(sequenceName)
                .orElseThrow(() -> new ResourceNotFoundException("Sequence not found : " + sequenceName));

        Long currentValue = codeSequence.getNextValue();

        codeSequence.setNextValue(currentValue + 1);

        codeSequenceRepository.save(codeSequence);

        return currentValue;
    }

    @Override
    public String generateCustomerCode() {
        return String.format("CUST%06d", getNextValue("CUSTOMER"));
    }

    @Override
    public String generateAccountNumber() {
        return String.format("ACC%06d", getNextValue("ACCOUNT"));
    }

    @Override
    public String generateTransactionNumber() {
        return String.format("TXN%08d", getNextValue("TRANSACTION"));
    }

    @Override
    public String generateEmployeeCode() {
        return String.format("EMP%06d", getNextValue("EMPLOYEE"));
    }
}