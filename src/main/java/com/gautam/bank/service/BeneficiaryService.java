package com.gautam.bank.service;

import com.gautam.bank.dto.request.beneficiary.BeneficiaryRequest;
import com.gautam.bank.dto.request.beneficiary.BeneficiaryTransferRequest;
import com.gautam.bank.dto.request.beneficiary.UpdateBeneficiaryRequest;
import com.gautam.bank.dto.response.beneficiary.BeneficiaryResponse;
import com.gautam.bank.dto.response.common.ApiResponse;
import com.gautam.bank.dto.response.statement.StatementResponse;
import com.gautam.bank.dto.response.transaction.TransactionResponse;

import java.util.*;

public interface BeneficiaryService {

    BeneficiaryResponse addBeneficiary(BeneficiaryRequest request);

    List<BeneficiaryResponse> getMyBeneficiaries();

    BeneficiaryResponse updateBeneficiary(String beneficiaryCode, UpdateBeneficiaryRequest request);

    ApiResponse deleteBeneficiary(String beneficiaryCode);

    TransactionResponse transferToBeneficiary(BeneficiaryTransferRequest request);

    // List<StatementResponse> getMiniStatement(String accountNumber);

}