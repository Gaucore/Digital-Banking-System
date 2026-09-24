package com.gautam.bank.service.impl;

import org.springframework.stereotype.Service;

import com.gautam.bank.entity.sequence.CodeSequence;
import com.gautam.bank.exception.ResourceNotFoundException;
import com.gautam.bank.repository.CodeSequenceRepository;
import com.gautam.bank.service.CodeSequenceService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CodeSequenceServiceImpl implements CodeSequenceService {

    private final CodeSequenceRepository codeSequenceRepository;

    @Transactional
    private Long getNextValue(String sequenceName) {
        CodeSequence codeSequence = codeSequenceRepository.findBySequenceName(sequenceName)
                .orElseThrow(() -> new ResourceNotFoundException("Sequence not found : " + sequenceName));

        Long currentValue = codeSequence.getNextValue();
        codeSequence.setNextValue(currentValue + 1);
        codeSequenceRepository.save(codeSequence);

        return currentValue;
    }

    @Override
    public String generateCustomerCode() {
        Long value = getNextValue("CUSTOMER");
        return String.format("CUST%06d", value);
    }

    @Override
    public String generateAccountNumber() {
        Long value = getNextValue("ACCOUNT");
        return String.valueOf(value);
    }

    @Override
    public String generateTransactionNumber() {
        Long value = getNextValue("TRANSACTION");
        return String.format("TXN%08d", value);
    }
}
