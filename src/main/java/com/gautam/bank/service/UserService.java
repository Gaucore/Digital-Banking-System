package com.gautam.bank.service;

import com.gautam.bank.dto.request.auth.LoginRequest;
import com.gautam.bank.dto.response.auth.LoginResponse;

public interface UserService {

    LoginResponse login(LoginRequest request);

}
