package com.gautam.bank.service;

import com.gautam.bank.dto.request.auth.LoginRequest;
import com.gautam.bank.dto.response.auth.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);
}
