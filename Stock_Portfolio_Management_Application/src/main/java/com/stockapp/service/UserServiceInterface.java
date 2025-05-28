package com.stockapp.service;

import com.stockapp.dto.AuthRequest;
import com.stockapp.dto.AuthResponse;
import com.stockapp.dto.RegisterRequest;

public interface UserServiceInterface {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(AuthRequest request);
}
